package com.chicchoc.sivillage.domain.oauth.application;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import com.chicchoc.sivillage.domain.oauth.domain.OauthMember;
import com.chicchoc.sivillage.domain.oauth.dto.in.OauthSignInRequestDto;
import com.chicchoc.sivillage.domain.oauth.dto.in.OauthSignUpRequestDto;
import com.chicchoc.sivillage.domain.oauth.dto.in.OauthUserInfoReqestDto;
import com.chicchoc.sivillage.domain.oauth.dto.out.OauthResponse;
import com.chicchoc.sivillage.domain.oauth.dto.out.OauthUserInfoResponseDto;
import com.chicchoc.sivillage.domain.oauth.infrastructure.OauthMemberRepository;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.chicchoc.sivillage.global.jwt.application.JwtTokenProvider;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthServiceImpl implements OauthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final OauthMemberRepository oauthMemberRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SignInResponseDto oauthSignUp(OauthSignUpRequestDto requestDto) {

        String uuid = NanoIdGenerator.generateNanoId();

        // 중복된 이름과 전화번호가 존재할 경우 예외 처리
        if (memberRepository.existsByNameAndPhone(requestDto.getName(), requestDto.getPhone())) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_NAME_AND_PHONE);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = requestDto.toEntity(encodedPassword, uuid);

        memberRepository.save(member);

        return oauthSignIn(OauthSignInRequestDto.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .oauthProvider(requestDto.getOauthProvider())
                .oauthId(requestDto.getOauthId())
                .oauthEmail(requestDto.getOauthEmail())
                .build());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SignInResponseDto oauthSignIn(OauthSignInRequestDto requestDto) {

        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));

        Authentication authentication = authenticate(member, requestDto.getPassword());

        linkOauthAccount(requestDto.getOauthId(), requestDto.getOauthProvider(), requestDto.getOauthEmail(), member);

        String accessToken = jwtTokenProvider.generateToken(authentication, jwtProperties.getAccessExpireTime());
        String refreshToken = jwtTokenProvider.generateToken(authentication, jwtProperties.getRefreshExpireTime());

        return new SignInResponseDto(accessToken, refreshToken, member.getUuid());
    }

    @Override
    public OauthResponse returnUserInfoOrSignIn(OauthUserInfoReqestDto requestDto) {

        // 연동된 계정이 있는지 확인
        OauthMember oauthMember = oauthMemberRepository.findByOauthIdAndOauthProvider(
                        requestDto.getOauthId(),
                        requestDto.getOauthProvider())
                .orElse(null);

        // 연동된 계정이 없는 경우 => 프론트로 OAuth 정보 전달.)
        if (oauthMember == null) {
            return OauthUserInfoResponseDto.of(requestDto);
        }

        Authentication authentication = manualAuthenticate(oauthMember.getMember());

        return SignInResponseDto.builder()
                .accessToken(jwtTokenProvider.generateToken(authentication, jwtProperties.getAccessExpireTime()))
                .refreshToken(jwtTokenProvider.generateToken(authentication, jwtProperties.getRefreshExpireTime()))
                .uuid(oauthMember.getMember().getUuid())
                .build();
    }

    private Authentication authenticate(Member member, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(member.getUuid(), password)
        );
    }

    private Authentication manualAuthenticate(Member member) {

        // todo: 필요시 권한 정보 추가
        Collection<? extends GrantedAuthority> authorities = member.getAuthorities();

        // Spring Security의 User 객체 생성
        User principal = new User(member.getUuid(), "", authorities);

        // UsernamePasswordAuthenticationToken 생성
        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    private void linkOauthAccount(String oauthId, String oauthProvider, String oauthEmail, Member member) {
        if (!oauthMemberRepository.existsByOauthIdAndOauthProvider(oauthId, oauthProvider)) {
            OauthMember oauthMember = new OauthMember(oauthId, oauthProvider, oauthEmail, member);
            oauthMemberRepository.save(oauthMember);
        }
    }
}
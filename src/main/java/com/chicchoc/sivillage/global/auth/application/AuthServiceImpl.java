package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import com.chicchoc.sivillage.domain.oauth.infrastructure.OauthMemberRepository;
import com.chicchoc.sivillage.domain.terms.domain.UserTermsList;
import com.chicchoc.sivillage.domain.terms.infrastructure.UserTermsListRepository;
import com.chicchoc.sivillage.global.auth.domain.EmailVerification;
import com.chicchoc.sivillage.global.auth.dto.in.CheckEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.CheckEmailVerificationRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.EmailVerificationRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.FindEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignInRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignUpRequestDto;
import com.chicchoc.sivillage.global.auth.dto.out.FindEmailResponseDto;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;
import com.chicchoc.sivillage.global.auth.infrastructure.EmailVerificationRepository;
import com.chicchoc.sivillage.global.auth.provider.EmailProvider;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.generator.VerificationCode;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.chicchoc.sivillage.global.jwt.application.JwtTokenProvider;
import com.chicchoc.sivillage.global.jwt.application.RefreshTokenService;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final UserTermsListRepository userTermsListRepository;
    private final OauthMemberRepository oauthMemberRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailProvider emailProvider;
    private final RefreshTokenService refreshTokenService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public SignInResponseDto signUpAndSignIn(SignUpRequestDto requestDto) {

        // 정책상 이메일 중복 검사는 이미 진행됨
        // 중복된 이름과 전화번호가 존재할 경우 예외 처리
        if (memberRepository.existsByNameAndPhone(requestDto.getName(), requestDto.getPhone())) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_NAME_AND_PHONE);
        }

        // 회원 정보 저장
        Member member = memberRepository.save(requestDto.toEntity(passwordEncoder));

        // 약관 정보 추가
        userTermsListRepository.saveAll(UserTermsList.of(member, requestDto.getTerms()));

        // oAuth 연동(oauth 정보가 있는 경우)
        if (validOauth(requestDto.getOauthId(), requestDto.getOauthProvider())) {
            oauthMemberRepository.save(requestDto.toOauthEntity(member));
        }

        return respondSignIn(member, requestDto.getPassword());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SignInResponseDto signIn(SignInRequestDto requestDto) {

        //아이디 검증
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));

        // oAuth 연동
        if (validOauth(requestDto.getOauthId(), requestDto.getOauthProvider())) {
            oauthMemberRepository.save(requestDto.toOauthEntity(member));
        }

        return respondSignIn(member, requestDto.getPassword());
    }

    @Transactional(readOnly = true)
    @Override
    public void checkEmail(CheckEmailRequestDto requestDto) {

        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_EMAIL);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public FindEmailResponseDto findEmail(FindEmailRequestDto requestDto) {

        String email = memberRepository.findEmailByNameAndPhoneNumber(requestDto.getName(), requestDto.getPhone())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        return new FindEmailResponseDto(maskEmail(email));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendVerificationEmail(EmailVerificationRequestDto requestDto) {

        // Redis로 변경

        String userEmail = requestDto.getEmail();
        String verificationCode = VerificationCode.generateCode();
        // 이메일 전송, 실패시 예외처리
        boolean isSent = emailProvider.sendVerificationEmail(userEmail, verificationCode);

        if (!isSent) {
            log.error("이메일 전송에 실패했습니다.");
            throw new BaseException(BaseResponseStatus.FAILED_TO_SEND_EMAIL);
        }

        // 기존 이메일 인증 정보 삭제
        emailVerificationRepository.deleteByEmail(userEmail);

        // 이메일 인증 정보 저장
        emailVerificationRepository.save(new EmailVerification(userEmail, verificationCode));
    }

    @Override
    public void checkEmailVerification(
            CheckEmailVerificationRequestDto requestDto) {

        // 이메일 인증 정보 조회
        EmailVerification emailVerification = emailVerificationRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_EMAIL_ADDRESS));

        boolean isMatched = emailVerification.getVerificationCode().equals(requestDto.getVerificationCode())
                && emailVerification.getEmail().equals(requestDto.getEmail());
        boolean isExpired = emailVerification.getExpiresAt().isBefore(LocalDateTime.now());

        if (!isMatched) { // 인증코드 불일치
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL_CODE_NOT_MATCH);
        }
        if (isExpired) { // 인증코드 만료
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL_CODE_EXPIRED);
        }
    }

    private boolean validOauth(String oauthId, String oauthProvider) {
        // oAuth 정보가 없는 경우(일반 회원가입/로그인)
        if (oauthId == null || oauthProvider == null || oauthId.isBlank() || oauthProvider.isBlank()
                || oauthId.equals("string")) { // Swagger 테스트시 String으로 넘어오므로
            return false;
        }

        // 지원하는 oAuth 플랫폼인지 확인
        final Set<String> SupportedProviders = Set.of("kakao", "naver");
        if (!SupportedProviders.contains(oauthProvider)) {
            throw new BaseException(BaseResponseStatus.NO_SUPPORTED_PROVIDER);
        }

        // 이미 연동된 계정이 있는지 확인
        if (oauthMemberRepository.existsByOauthIdAndOauthProvider(oauthId, oauthProvider)) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_SOCIAL_USER);
        }

        return true;
    }

    private SignInResponseDto respondSignIn(Member member, String password) {

        String uuid = member.getUuid();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(uuid, password));

        //토큰 생성, 리프레쉬 토큰 저장, 응답
        String accessToken = jwtTokenProvider.generateToken(authentication, jwtProperties.getAccessExpireTime());
        String refreshToken = jwtTokenProvider.generateToken(authentication, jwtProperties.getRefreshExpireTime());
        refreshTokenService.saveOrUpdateRefreshToken(uuid, refreshToken);

        return new SignInResponseDto(accessToken, refreshToken, uuid);
    }

    private String maskEmail(String email) {
        String[] splitEmail = email.split("@");
        String local = splitEmail[0];
        String domain = splitEmail[1];
        int localLength = local.length();

        // 로컬 길이에 따라 마스킹 처리
        String maskedLocalPart;
        if (localLength > 3) {
            maskedLocalPart = local.substring(0, 3) + "*".repeat(localLength - 3);
        } else {
            maskedLocalPart = local.charAt(0) + "*".repeat(localLength - 1);
        }

        return maskedLocalPart + "@" + domain;
    }
}

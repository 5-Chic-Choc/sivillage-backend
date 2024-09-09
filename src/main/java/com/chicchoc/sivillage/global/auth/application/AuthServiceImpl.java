package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import com.chicchoc.sivillage.global.auth.dto.in.CheckEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.FindEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignInRequestDto;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignUpRequestDto;
import com.chicchoc.sivillage.global.common.aop.annotation.ExceptionHandleAop;
import com.chicchoc.sivillage.global.jwt.application.RefreshTokenService;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import com.chicchoc.sivillage.global.jwt.application.JwtTokenProvider;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.jwt.application.JwtTokenProvider;
import com.chicchoc.sivillage.global.jwt.application.RefreshTokenService;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import java.util.Optional;
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

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;

    @ExceptionHandleAop // save 중 예외 발생 시
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {

        String uuid = new NanoIdGenerator().generateNanoId();

        if (memberRepository.existsByNameAndPhone(signUpRequestDto.getName(), signUpRequestDto.getPhone())) {
            throw new IllegalArgumentException("이미 가입된 ID가 있습니다.");
        }

        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
        Member member = signUpRequestDto.toEntity(uuid, password);

        memberRepository.save(member);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {

        //아이디 검증
        Member member = memberRepository.findByEmail(signInRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다."));

        try {
            //인증 객체 생성
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            member.getUuid(),
                            signInRequestDto.getPassword()
                    )
            );

            //토큰 생성
            String accessToken = jwtTokenProvider.generateToken(authentication, jwtProperties.getAccessExpireTime());
            String refreshToken = jwtTokenProvider.generateToken(authentication, jwtProperties.getRefreshExpireTime());
            refreshTokenService.saveOrUpdateRefreshToken(member.getUuid(), refreshToken);

            return SignInResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .uuid(member.getUuid())
                    .build();

        } catch (Exception e) {
            log.warn("로그인 시도 실패 : {}", e.getMessage());
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }

    @ExceptionHandleAop
    @Transactional(readOnly = true)
    @Override
    public boolean isInUseEmail(CheckEmailRequestDto checkEmailRequestDto) {

        return memberRepository.existsByEmail(checkEmailRequestDto.getEmail());
    }

    @ExceptionHandleAop
    @Transactional(readOnly = true)
    @Override
    public Optional<String> findEmail(FindEmailRequestDto findEmailRequestDto) {

        String name = findEmailRequestDto.getName();
        String phone = findEmailRequestDto.getPhone();

        // 이메일이 존재할 경우 마스킹하여 반환, 없는 경우는
        return memberRepository.findEmailByNameAndPhoneNumber(name, phone)
                .map(this::maskEmail);
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

package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
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
import com.chicchoc.sivillage.global.common.aop.annotation.MethodLoggerAop;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.common.generator.VerificationCode;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.chicchoc.sivillage.global.jwt.application.JwtTokenProvider;
import com.chicchoc.sivillage.global.jwt.application.RefreshTokenService;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import java.time.LocalDateTime;
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
    private final EmailProvider emailProvider;
    private final EmailVerificationRepository emailVerificationRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SignInResponseDto signUpAndSignIn(SignUpRequestDto requestDto) {

        String uuid = new NanoIdGenerator().generateNanoId();

        // 중복된 이름과 전화번호가 존재할 경우 예외 처리
        if (memberRepository.existsByNameAndPhone(requestDto.getName(), requestDto.getPhone())) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_NAME_AND_PHONE);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        Member member = requestDto.toEntity(uuid, encodedPassword);

        memberRepository.save(member);

        return signIn(SignInRequestDto.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .isAutoSignIn(false)
                .build());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SignInResponseDto signIn(SignInRequestDto requestDto) {

        //아이디 검증
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOGIN));

        //인증 객체 생성
        Authentication authentication = authenticateMember(member, requestDto.getPassword());

        //토큰 생성
        String accessToken = jwtTokenProvider.generateToken(authentication, jwtProperties.getAccessExpireTime());
        String refreshToken = jwtTokenProvider.generateToken(authentication, jwtProperties.getRefreshExpireTime());
        refreshTokenService.saveOrUpdateRefreshToken(member.getUuid(), refreshToken);

        return new SignInResponseDto(accessToken, refreshToken, member.getUuid());
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

        String userEmail = requestDto.getEmail();
        String verificationCode = VerificationCode.generateCode();

        // 이메일 중복 확인
        checkEmail(CheckEmailRequestDto.builder().email(userEmail).build());

        // 전송, 실패시 예외처리
        boolean isSent = emailProvider.sendVerificationEmail(userEmail, verificationCode);
        if (!isSent) {
            log.error("이메일 전송에 실패했습니다.");
            throw new BaseException(BaseResponseStatus.FAILED_TO_SEND_EMAIL);
        }

        //기존 내용은 삭제
        emailVerificationRepository.deleteByEmail(userEmail);

        // 이메일 인증 정보 DB 저장
        EmailVerification emailVerification = EmailVerification.builder()
                .email(userEmail)
                .verificationCode(verificationCode)
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .build();

        emailVerificationRepository.save(emailVerification);
    }

    @Override
    public void checkEmailVerification(
            CheckEmailVerificationRequestDto requestDto) {

        String email = requestDto.getEmail();
        String verificationCode = requestDto.getVerificationCode();

        // 이메일 인증 정보 조회(여러개라면 마지막 것)
        EmailVerification emailVerification = emailVerificationRepository.findTopByEmailOrderByIdDesc(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_EMAIL_ADDRESS));

        boolean isMatched = emailVerification.getVerificationCode().equals(verificationCode)
                && emailVerification.getEmail().equals(email);
        boolean isExpired = emailVerification.getExpiresAt().isBefore(LocalDateTime.now());

        if (!isMatched) { // 인증코드 불일치
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL_CODE_NOT_MATCH);
        }
        if (isExpired) { // 인증코드 만료
            throw new BaseException(BaseResponseStatus.INVALID_EMAIL_CODE_EXPIRED);
        }
    }

    private Authentication authenticateMember(Member member, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        member.getUuid(), password
                )
        );
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

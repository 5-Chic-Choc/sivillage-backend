package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.global.auth.dto.SignInRequestDto;
import com.chicchoc.sivillage.global.auth.dto.SignUpRequestDto;
import com.chicchoc.sivillage.global.auth.infrastructure.AuthRepository;
import com.chicchoc.sivillage.global.auth.infrastructure.RefreshTokenRepository;
import com.chicchoc.sivillage.global.auth.jwt.JwtProperties;
import com.chicchoc.sivillage.global.auth.jwt.JwtTokenProvider;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final AuthRepository authRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {

        String uuid = UUID.randomUUID().toString();
        String password = passwordEncoder.encode(signUpRequestDto.getPassword());
        Member member = signUpRequestDto.toEntity(uuid, password);

        // 중복검사
        if (authRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 회원입니다.");
        }
        try {
            authRepository.save(member);
        } catch (Exception e) {
            log.error("Exception: {}", e);
            throw new IllegalArgumentException("회원가입에 실패하였습니다.");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String signIn(SignInRequestDto signInRequestDto) {

        Member member = authRepository.findByEmail(signInRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.")); //이메일 없음

        // 비밀번호 매칭
        if (!passwordEncoder.matches(signInRequestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다."); //비밀번호 틀림
        }

        // 토큰 생성
        String accessToken = jwtTokenProvider.generateToken(member,
                Duration.ofMillis(jwtProperties.getAccessExpireTime()));
        String refreshToken = jwtTokenProvider.generateToken(member,
                Duration.ofMillis(jwtProperties.getRefreshExpireTime()));

        refreshTokenService.saveOrUpdateRefreshToken(member.getUuid(), refreshToken);

        return accessToken;
    }
}

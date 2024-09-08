package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import com.chicchoc.sivillage.global.auth.dto.in.SignInRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignUpRequestDto;
import com.chicchoc.sivillage.global.auth.dto.out.CheckEmailResponseDto;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;
import com.chicchoc.sivillage.global.auth.exception.EmailAlreadyInUseException;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.jwt.application.JwtTokenProvider;
import com.chicchoc.sivillage.global.jwt.application.RefreshTokenService;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void signUp(SignUpRequestDto signUpRequestDto) {

        String uuid = new NanoIdGenerator().generateNanoId();

        // uuid 중복 검사
        if (memberRepository.findByUuid(uuid).isPresent()) {
            throw new IllegalArgumentException("문제가 발생했습니다. 다시 시도해주세요.");
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
            //로그인 시도
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            member.getUuid(),
                            signInRequestDto.getPassword()
                    )
            );

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

    @Transactional(readOnly = true)
    @Override
    public CheckEmailResponseDto checkEmail(String email) {

        CheckEmailResponseDto responseDto = new CheckEmailResponseDto();

        if (memberRepository.findByEmail(email).isPresent()) {
            responseDto.setAvailable(false);
            throw new EmailAlreadyInUseException("이미 사용중인 이메일입니다.");
        } else {
            responseDto.setAvailable(true);
            responseDto.setMessage("사용 가능한 이메일입니다.");
        }

        return responseDto;
    }

}

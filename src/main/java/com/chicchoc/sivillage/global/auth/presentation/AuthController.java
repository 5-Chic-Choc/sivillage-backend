package com.chicchoc.sivillage.global.auth.presentation;

import com.chicchoc.sivillage.global.auth.application.AuthService;
import com.chicchoc.sivillage.global.auth.dto.in.CheckEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.CheckEmailVerificationRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.EmailVerificationRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.FindEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignInRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignUpRequestDto;
import com.chicchoc.sivillage.global.auth.dto.out.FindEmailResponseDto;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;
import com.chicchoc.sivillage.global.auth.vo.SignInResponseVo;
import com.chicchoc.sivillage.global.common.aop.annotation.MethodLoggerAop;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 관련", description = "회원가입, 로그인 페이지 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtProperties jwtProperties;

    @Operation(summary = "회원가입", description = "회원가입 + 로그인(토큰 응답)")
    @PostMapping("/sign-up")
    public BaseResponse<SignInResponseVo> signUp(@Valid @RequestBody SignUpRequestDto requestDto,
            HttpServletResponse response) {

        // 회원가입 후 로그인 처리 (SignIn 호출)
        authService.signUp(requestDto);
        SignInResponseDto responseDto = authService.signIn(
                new SignInRequestDto(requestDto.getEmail(), requestDto.getPassword())
        );

        return sendTokens(responseDto, response);
    }

    @Operation(summary = "로그인 ", description = "로그인(토큰 2종 헤더에 발급)")
    @PostMapping("/sign-in")
    public BaseResponse<SignInResponseVo> signIn(@Valid @RequestBody SignInRequestDto signInRequestDto,
            HttpServletResponse response) {

        SignInResponseDto responseDto = authService.signIn(signInRequestDto);
        return sendTokens(responseDto, response);
    }

    // 이메일 중복 검사
    @Operation(summary = "이메일 중복 검사", description = "이메일 중복 검사")
    @PostMapping("/check-email")
    public BaseResponse<Void> checkEmail(
            @Valid @RequestBody CheckEmailRequestDto checkEmailRequestDto) {

        authService.checkEmail(checkEmailRequestDto);
        return new BaseResponse<>();
    }

    // email 찾기
    @Operation(summary = "이메일 찾기", description = "이메일 찾기")
    @PostMapping("/find-email")
    public BaseResponse<FindEmailResponseDto> findEmail(
            @Valid @RequestBody FindEmailRequestDto findEmailRequestDto) {

        return new BaseResponse<>(authService.findEmail(findEmailRequestDto));
    }

    @MethodLoggerAop
    @Operation(summary = "이메일 인증코드 전송", description = "이메일 인증코드 전송")
    @PostMapping("/email-verification")
    public BaseResponse<Void> emailVerification(@Valid @RequestBody EmailVerificationRequestDto requestDto) {

        authService.sendVerificationEmail(requestDto);

        return new BaseResponse<>();
    }

    @Operation(summary = "이메일 인증코드 검증", description = "이메일 인증코드 검증")
    @PostMapping("/check-email-verification")
    public BaseResponse<Void> checkEmailVerification(
            @Valid @RequestBody CheckEmailVerificationRequestDto requestDto) {

        authService.checkEmailVerification(requestDto);

        return new BaseResponse<>();
    }

    //로그인 공통 로직
    private BaseResponse<SignInResponseVo> sendTokens(SignInResponseDto responseDto,
            HttpServletResponse response) {

        response.setHeader(jwtProperties.getAccessTokenPrefix(), responseDto.getAccessToken());
        response.setHeader(jwtProperties.getRefreshTokenPrefix(), responseDto.getRefreshToken());

        // responseDto -> responseVo 변환해 응답
        return new BaseResponse<>(responseDto.toVo());
    }
}

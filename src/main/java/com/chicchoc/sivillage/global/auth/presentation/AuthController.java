package com.chicchoc.sivillage.global.auth.presentation;

import com.chicchoc.sivillage.global.auth.application.AuthService;
import com.chicchoc.sivillage.global.auth.dto.in.CheckEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignInRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignUpRequestDto;
import com.chicchoc.sivillage.global.auth.dto.out.CheckEmailResponseDto;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;
import com.chicchoc.sivillage.global.auth.vo.SignInResponseVo;
import com.chicchoc.sivillage.global.common.aop.annotation.ValidAop;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtProperties jwtProperties;

    @Operation(summary = "회원가입", description = "회원가입과 동시에 로그인이 됩니다.", tags = {"Auth"})
    @ValidAop
    @PostMapping("/sign-up")
    public CommonResponseEntity<SignInResponseVo> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto,
            BindingResult bindingResult, HttpServletResponse response) {

        authService.signUp(signUpRequestDto);

        // 회원가입 후 로그인 처리 (SignIn 호출)
        SignInResponseDto responseDto = authService.signIn(
                new SignInRequestDto(signUpRequestDto.getEmail(), signUpRequestDto.getPassword())
        );

        return signInResponse(responseDto, response);
    }

    @Operation(summary = "로그인 ", description = "로그인", tags = {"Auth"})
    @ValidAop
    @PostMapping("/sign-in")
    public CommonResponseEntity<SignInResponseVo> signIn(@Valid @RequestBody SignInRequestDto signInRequestDto,
            BindingResult bindingResult, HttpServletResponse response) {

        SignInResponseDto responseDto = authService.signIn(signInRequestDto);

        return signInResponse(responseDto, response);
    }

    // 이메일 중복 검사
    @Operation(summary = "이메일 중복 검사", description = "이메일 중복 검사", tags = {"Auth"})
    @ValidAop
    @PostMapping("/check-email")
    public CommonResponseEntity<CheckEmailResponseDto> checkEmail(
            @Valid @RequestBody CheckEmailRequestDto checkEmailRequestDto,
            BindingResult bindingResult) {

        CheckEmailResponseDto responseDto = authService.checkEmail(checkEmailRequestDto.getEmail());

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                responseDto.getMessage(),
                responseDto
        );
    }

    //로그인 공통 로직
    private CommonResponseEntity<SignInResponseVo> signInResponse(SignInResponseDto responseDto,
            HttpServletResponse response) {

        response.setHeader(jwtProperties.getHeaderString(), responseDto.getAccessToken());
        response.setHeader("RefreshToken", responseDto.getRefreshToken());

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "로그인이 완료되었습니다.",
                responseDto.toVo(responseDto)
        );
    }
}

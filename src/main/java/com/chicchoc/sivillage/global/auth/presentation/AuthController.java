package com.chicchoc.sivillage.global.auth.presentation;

import com.chicchoc.sivillage.global.auth.application.AuthService;
import com.chicchoc.sivillage.global.auth.dto.in.CheckEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.FindEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignInRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignUpRequestDto;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;
import com.chicchoc.sivillage.global.auth.vo.SignInResponseVo;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/sign-up")
    public CommonResponseEntity<SignInResponseVo> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto,
            HttpServletResponse response) {

        authService.signUp(signUpRequestDto);

        // 회원가입 후 로그인 처리 (SignIn 호출)
        SignInResponseDto responseDto = authService.signIn(
                new SignInRequestDto(signUpRequestDto.getEmail(), signUpRequestDto.getPassword())
        );

        return signInResponse(responseDto, response, 0); // 0: 회원가입
    }

    @Operation(summary = "로그인 ", description = "로그인", tags = {"Auth"})
    @PostMapping("/sign-in")
    public CommonResponseEntity<SignInResponseVo> signIn(@Valid @RequestBody SignInRequestDto signInRequestDto,
            HttpServletResponse response) {

        SignInResponseDto responseDto = authService.signIn(signInRequestDto);

        return signInResponse(responseDto, response, 1); // 1 : 로그인
    }

    // 이메일 중복 검사
    @Operation(summary = "이메일 중복 검사", description = "이메일 중복 검사", tags = {"Auth"})
    @PostMapping("/check-email")
    public CommonResponseEntity<Boolean> checkEmail(
            @Valid @RequestBody CheckEmailRequestDto checkEmailRequestDto) {

        boolean isInUse = authService.isInUseEmail(checkEmailRequestDto);

        return new CommonResponseEntity<>(isInUse ? HttpStatus.CONFLICT : HttpStatus.OK,
                isInUse ? "이미 사용중인 이메일입니다." : "사용 가능한 이메일입니다.", !isInUse);
    }

    // email 찾기
    @Operation(summary = "이메일 찾기", description = "이메일 찾기", tags = {"Auth"})
    @PostMapping("/find-email")
    public CommonResponseEntity<String> findEmail(
            @Valid @RequestBody FindEmailRequestDto findEmailRequestDto) {

        Optional<String> emailResult = authService.findEmail(findEmailRequestDto);

        return emailResult.map(email ->
                        new CommonResponseEntity<>(HttpStatus.OK, "이메일 찾기가 성공하였습니다.", email))
                .orElse(new CommonResponseEntity<>(HttpStatus.NOT_FOUND, "해당하는 회원 정보가 없습니다.", null));
    }

    //로그인 공통 로직
    private CommonResponseEntity<SignInResponseVo> signInResponse(SignInResponseDto responseDto,
            HttpServletResponse response, int type) {

        response.setHeader(jwtProperties.getAccessTokenPrefix(), responseDto.getAccessToken());
        response.setHeader(jwtProperties.getRefreshTokenPrefix(), responseDto.getRefreshToken());

        return new CommonResponseEntity<>(
                type == 0 ? HttpStatus.CREATED : HttpStatus.OK,
                type == 0 ? "회원가입이 성공하였습니다." : "로그인이 성공하였습니다.",
                responseDto.toVo(responseDto)
        );
    }
}

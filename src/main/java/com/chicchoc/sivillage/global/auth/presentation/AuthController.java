package com.chicchoc.sivillage.global.auth.presentation;

import com.chicchoc.sivillage.global.auth.application.AuthService;
import com.chicchoc.sivillage.global.auth.dto.SignUpRequestDto;
import com.chicchoc.sivillage.global.common.aop.annotation.ValidAop;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @ValidAop
    @Operation(summary = "SignUp API", description = "회원가입", tags = {"Auth"})
    @PostMapping("/sign-up")
    public CommonResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto,
            BindingResult bindingResult) {

        authService.signUp(signUpRequestDto);

        return new CommonResponseEntity<>(
                HttpStatus.CREATED,
                "회원가입이 완료되었습니다.",
                null);
    }

}

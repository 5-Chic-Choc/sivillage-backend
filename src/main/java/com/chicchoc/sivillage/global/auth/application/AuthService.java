package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.global.auth.dto.in.SignInRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignUpRequestDto;
import com.chicchoc.sivillage.global.auth.dto.out.CheckEmailResponseDto;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;

public interface AuthService {

    void signUp(SignUpRequestDto signUpRequestDto);

    SignInResponseDto signIn(SignInRequestDto signInRequestDto);

    CheckEmailResponseDto checkEmail(String email);
}

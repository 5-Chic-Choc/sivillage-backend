package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.global.auth.dto.in.CheckEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.CheckEmailVerificationRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.EmailVerificationRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.FindEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignInRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignUpRequestDto;
import com.chicchoc.sivillage.global.auth.dto.out.FindEmailResponseDto;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;

public interface AuthService {

    void signUp(SignUpRequestDto requestDto);

    SignInResponseDto signIn(SignInRequestDto requestDto);

    void checkEmail(CheckEmailRequestDto requestDto);

    FindEmailResponseDto findEmail(FindEmailRequestDto requestDto);

    void verifyEmail(EmailVerificationRequestDto requestDto);

    void checkEmailVerification(CheckEmailVerificationRequestDto requestDto);
}

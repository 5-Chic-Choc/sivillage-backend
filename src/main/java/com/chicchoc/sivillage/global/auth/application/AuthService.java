package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.global.auth.dto.in.CheckEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.FindEmailRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignInRequestDto;
import com.chicchoc.sivillage.global.auth.dto.in.SignUpRequestDto;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;
import java.util.Optional;

public interface AuthService {

    void signUp(SignUpRequestDto signUpRequestDto);

    SignInResponseDto signIn(SignInRequestDto signInRequestDto);

    boolean isInUseEmail(CheckEmailRequestDto checkEmailRequestDto);

    Optional<String> findEmail(FindEmailRequestDto findEmailRequestDto);
}

package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.global.auth.dto.SignUpRequestDto;

public interface AuthService {

    void signUp(SignUpRequestDto signUpRequestDto);
}

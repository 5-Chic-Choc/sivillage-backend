package com.chicchoc.sivillage.domain.oauth.application;

import com.chicchoc.sivillage.domain.oauth.dto.in.OauthSignInRequestDto;
import com.chicchoc.sivillage.domain.oauth.dto.in.OauthSignUpRequestDto;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;

public interface OauthService {

    SignInResponseDto oauthSignUp(OauthSignUpRequestDto requestDto);

    SignInResponseDto oauthSignIn(OauthSignInRequestDto requestDto);
}

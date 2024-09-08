package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.global.auth.jwt.JwtProperties;
import com.chicchoc.sivillage.global.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    public String createNewAccessToken(String refreshToken) {

        if (!jwtTokenProvider.isValidToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        Authentication authentication = jwtTokenProvider.createAuthentication(refreshToken);

        return jwtTokenProvider.generateToken(authentication, jwtProperties.getAccessExpireTime());
    }

}

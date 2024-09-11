package com.chicchoc.sivillage.global.jwt.application;

import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
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
            throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
        }

        Authentication authentication = jwtTokenProvider.createAuthentication(refreshToken);

        return jwtTokenProvider.generateToken(authentication, jwtProperties.getAccessExpireTime());
    }

}

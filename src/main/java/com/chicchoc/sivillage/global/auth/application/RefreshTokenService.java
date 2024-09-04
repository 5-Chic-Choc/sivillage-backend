package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.global.auth.domain.RefreshToken;
import com.chicchoc.sivillage.global.auth.infrastructure.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {

        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("해당 리프레시 토큰을 찾을 수 없습니다."));
    }
}

package com.chicchoc.sivillage.global.jwt.application;

import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.chicchoc.sivillage.global.jwt.domain.RefreshToken;
import com.chicchoc.sivillage.global.jwt.infrastructure.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {

        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN));
    }

    @Transactional
    public void saveOrUpdateRefreshToken(String uuid, String newRefreshToken) {
        RefreshToken existingToken = refreshTokenRepository.findByUuid(uuid)
                .orElse(null);

        if (existingToken != null) {
            // 토큰이 존재하면 수정
            existingToken.setRefreshToken(newRefreshToken);
        } else {
            // 없으면 새로 저장
            RefreshToken newToken = new RefreshToken(uuid, newRefreshToken);
            refreshTokenRepository.save(newToken);
        }
    }
}

package com.chicchoc.sivillage.global.jwt.application;

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

    @Transactional
    public void saveOrUpdateRefreshToken(String uuid, String newRefreshToken) {

        refreshTokenRepository.findByUuid(uuid)
            .ifPresentOrElse(
                    existingToken -> existingToken.update(newRefreshToken), // 토큰이 존재하면 수정
                    () -> refreshTokenRepository.save(new RefreshToken(uuid, newRefreshToken)) // 없으면 새로 저장
            );
    }
}

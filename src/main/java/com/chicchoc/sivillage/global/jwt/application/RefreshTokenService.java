package com.chicchoc.sivillage.global.jwt.application;

import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final JwtProperties jwtProperties;

    private final RedisTemplate<String, Object> redisTemplate;


    // Redis에 Refresh Token 저장/업데이트
    public void saveOrUpdateRefreshToken(String uuid, String refreshToken) {
        String key = jwtProperties.getRefreshTokenPrefix() + uuid;

        // Redis에 토큰 저장, 유효기간 설정
        redisTemplate.opsForValue().set(key, refreshToken, jwtProperties.getRefreshExpireTime(), TimeUnit.MILLISECONDS);

        log.info("Refresh Token 저장됨: {} - {}", uuid, refreshToken);
    }

    // Redis에서 Refresh Token 조회
    public Optional<String> findRefreshTokenByUuid(String uuid) {
        String key = jwtProperties.getRefreshTokenPrefix() + uuid;

        String refreshToken = (String) redisTemplate.opsForValue().get(key);

        return Optional.ofNullable(refreshToken);
    }
}

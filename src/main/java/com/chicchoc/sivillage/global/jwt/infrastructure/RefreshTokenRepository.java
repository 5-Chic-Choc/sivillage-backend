package com.chicchoc.sivillage.global.jwt.infrastructure;

import com.chicchoc.sivillage.global.jwt.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUuid(String uuid);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void deleteByUuid(String uuid);

}

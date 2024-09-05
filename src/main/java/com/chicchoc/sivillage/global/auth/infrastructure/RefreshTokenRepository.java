package com.chicchoc.sivillage.global.auth.infrastructure;

import com.chicchoc.sivillage.global.auth.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUuid(String uuid);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

}

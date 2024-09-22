package com.chicchoc.sivillage.domain.best.infrastructure;

import com.chicchoc.sivillage.domain.best.domain.ProductScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductScoreRepository extends JpaRepository<ProductScore, Long> {

    Optional<ProductScore> findByProductUuid(String productUuid);
}
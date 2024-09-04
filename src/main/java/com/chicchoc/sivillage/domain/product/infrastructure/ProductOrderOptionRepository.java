package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.ProductOrderOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductOrderOptionRepository extends JpaRepository<ProductOrderOption, Long> {
    Optional<ProductOrderOption> findFirstByProductIdOrderByIdAsc(Long productId);
}

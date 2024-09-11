package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductOrderOptionRepository extends JpaRepository<ProductOption, Long> {
}

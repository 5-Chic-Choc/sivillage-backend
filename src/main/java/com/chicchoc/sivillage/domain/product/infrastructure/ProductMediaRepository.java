package com.chicchoc.sivillage.domain.media.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductMediaRepository extends JpaRepository<ProductMedia, Long> {
    Optional<ProductMedia> findFirstByProductOrderOptionIdOrderByMediaOrderAsc(Long productOrderOptionId);
}

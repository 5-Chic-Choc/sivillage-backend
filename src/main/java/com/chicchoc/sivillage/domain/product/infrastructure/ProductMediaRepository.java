package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductMediaRepository extends JpaRepository<ProductMedia, Long> {

    // 제품 옵션 UUID로 제품 미디어 조회
    List<ProductMedia> findByProductOptionUuid(String productOptionUuid);
}

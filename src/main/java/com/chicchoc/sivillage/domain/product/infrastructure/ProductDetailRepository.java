package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findByProductOptionUuid(String productOptionUuid);
}

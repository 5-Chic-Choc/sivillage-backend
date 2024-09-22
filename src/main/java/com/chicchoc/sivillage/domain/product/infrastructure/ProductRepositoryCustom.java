package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;

import java.util.List;

public interface ProductRepositoryCustom {

    // 필터링된 제품 목록을 가져오는 메서드
    List<Product> findFilteredProducts(ProductRequestDto dto);
}

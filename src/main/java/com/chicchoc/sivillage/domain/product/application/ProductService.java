package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.dto.out.ProductResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> findAllByBrandId(Long brandId);
}

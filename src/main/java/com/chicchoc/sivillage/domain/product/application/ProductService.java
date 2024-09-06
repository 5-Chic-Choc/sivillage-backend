package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductPerBrandResponseDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductPerBrandResponseDto> findAllByBrandId(Long brandId);

    List<ProductResponseDto> getFilteredProducts(ProductRequestDto productRequestDto);
}

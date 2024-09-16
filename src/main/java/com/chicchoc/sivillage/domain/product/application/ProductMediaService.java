package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.dto.out.ProductMediaResponseDto;

import java.util.List;

public interface ProductMediaService {
    List<ProductMediaResponseDto> getProductMediaByOptionUuid(String productOptionUuid);
}
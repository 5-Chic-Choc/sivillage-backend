package com.chicchoc.sivillage.domain.product.dto.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponseDto {
    private String productUUID;
    private String sourceUrl;
    private String brandName;
    private String productName;
    private int price;
}
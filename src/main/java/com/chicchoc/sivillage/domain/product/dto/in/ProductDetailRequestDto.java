package com.chicchoc.sivillage.domain.product.dto.in;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDetailRequestDto {
    private String productUuid;
}

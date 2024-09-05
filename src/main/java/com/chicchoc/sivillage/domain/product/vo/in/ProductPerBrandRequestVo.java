package com.chicchoc.sivillage.domain.product.vo.in;

import com.chicchoc.sivillage.domain.product.dto.in.ProductPerBrandRequestDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductPerBrandRequestVo {
    private Long brandId;

    public ProductPerBrandRequestDto toRequestDto() {
        return ProductPerBrandRequestDto.builder()
                .brandId(brandId)
                .build();
    }
}

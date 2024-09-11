package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.vo.out.ProductResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ProductResponseDto {
    private String productUuid;
    private String brandUuid;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProductOptionResponseDto> productOptions;

    // VO로 변환하는 메소드
    public ProductResponseVo toResponseVo() {
        return ProductResponseVo.builder()
                .productUuid(productUuid)
                .brandUuid(brandUuid)
                .name(name)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}

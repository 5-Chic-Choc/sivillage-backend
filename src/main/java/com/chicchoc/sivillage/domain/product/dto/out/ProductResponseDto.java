package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.vo.out.ProductResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponseDto {
    private Long productId;
    private String productUuid;
    private String brandUuid;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getId())
                .productUuid(product.getProductUuid())
                .brandUuid(product.getBrandUuid())
                .name(product.getProductName())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    // VO로 변환하는 메소드
    public ProductResponseVo toResponseVo() {
        return ProductResponseVo.builder()
                .productId(productId)
                .productUuid(productUuid)
                .brandUuid(brandUuid)
                .name(name)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}

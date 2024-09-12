package com.chicchoc.sivillage.domain.product.vo.out;

import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.dto.out.ProductOptionResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ProductResponseVo {
    private String productUuid;
    private String brandUuid;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProductOptionResponseDto> productOptions;

    public static ProductResponseVo fromEntity(Product product) {
        return ProductResponseVo.builder()
                .productUuid(product.getProductUuid())
                .brandUuid(product.getBrandUuid())
                .name(product.getProductName())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .productOptions(product.getProductOptions().stream()
                        .map(option -> ProductOptionResponseDto.fromEntity(option))
                        .collect(Collectors.toList()))
                .build();
    }
}

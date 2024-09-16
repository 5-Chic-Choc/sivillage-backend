package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.domain.ProductMedia;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ProductMediaResponseDto {
    private Long id;
    private String productOptionUuid;
    private Long mediaId;
    private int mediaOrder;

    public static ProductMediaResponseDto fromEntity(ProductMedia productMedia) {
        return ProductMediaResponseDto.builder()
                .id(productMedia.getId())
                .productOptionUuid(productMedia.getProductOptionUuid())
                .mediaId(productMedia.getMediaId())
                .mediaOrder(productMedia.getMediaOrder())
                .build();
    }
}
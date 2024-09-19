package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.domain.ProductDetail;
import com.chicchoc.sivillage.domain.product.vo.out.ProductDetailResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDetailResponseDto {
    private Long id;

    private String productDetailUrl;

    public ProductDetailResponseVo toVo() {
        return ProductDetailResponseVo.builder()
                .id(id)
                .productDetailUrl(productDetailUrl)
                .build();
    }

    public static ProductDetailResponseDto fromEntity(ProductDetail productDetail) {
        return ProductDetailResponseDto.builder()
                .id(productDetail.getId())
                .productDetailUrl(productDetail.getProductDetailUrl())
                .build();
    }
}

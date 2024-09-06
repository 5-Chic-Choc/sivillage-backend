package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.vo.out.ProductDetailResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductDetailResponseDto {
    private String productUuid;
    private String productName;
    private Long brandId;
    private LocalDateTime createdAt;

    private Long sizeId;
    private Long colorId;
    private int price;
    private int discountPrice;
    private int discountRate;

    private String productDetailUrl;

    public ProductDetailResponseVo toResponseVo() {
        return ProductDetailResponseVo.builder()
                .productUuid(productUuid)
                .productName(productName)
                .brandId(brandId)
                .createdAt(createdAt)
                .sizeId(sizeId)
                .colorId(colorId)
                .price(price)
                .discountPrice(discountPrice)
                .discountRate(discountRate)
                .build();
    }

}

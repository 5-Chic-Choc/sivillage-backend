package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.vo.out.ProductPerBrandResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ProductPerBrandResponseDto {
    private String productUuid;
    private String brandName;
    private String productName;
    private int price;
    private int discountRate;
    private int discountPrice;
    private int originalPrice;
    private LocalDateTime createdAt;
    private Long brandId;

    public ProductPerBrandResponseVo toResponseVo() {
        return ProductPerBrandResponseVo.builder()
                .productUuid(productUuid)
                .brandName(brandName)
                .productName(productName)
                .price(price)
                .discountRate(discountRate)
                .discountPrice(discountPrice)
                .originalPrice(originalPrice)
                .createdAt(createdAt)
                .brandId(brandId)
                .build();
    }
}

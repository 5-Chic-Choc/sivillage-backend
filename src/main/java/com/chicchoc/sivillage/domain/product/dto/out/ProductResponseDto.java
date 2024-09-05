package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.vo.out.ProductResponseVo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponseDto {
    private String productUuid;
    private String productName;
    private int price;
    private int discountRate;
    private int discountPrice;
    private int originalPrice;
    private LocalDateTime createdAt;
    private Long brandId;

    // VO로 변환하는 메소드
    public ProductResponseVo toResponseVo() {
        return ProductResponseVo.builder()
                .productUuid(productUuid)
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

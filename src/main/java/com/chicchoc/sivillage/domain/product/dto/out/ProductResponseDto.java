package com.chicchoc.sivillage.domain.product.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
public class ProductResponseDto {
    private String productUuid;
    private String productName;
    private int price;
    private int discountRate;
    private int discountPrice;
    private int originalPrice;
    private LocalDateTime createdAt;
    private Long brandId;

    public ProductResponseDto(String productUuid, String productName, int price, int discountRate, int discountPrice, int originalPrice, LocalDateTime createdAt, Long brandId) {
        this.productUuid = productUuid;
        this.productName = productName;
        this.price = price;
        this.discountRate = discountRate;
        this.discountPrice = discountPrice;
        this.originalPrice = originalPrice;
        this.createdAt = createdAt;
        this.brandId = brandId;
    }

}
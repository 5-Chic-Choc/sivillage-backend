package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductPerBrandResponseVo {
    private String productUuid;
    private String brandName;
    private String productName;
    private int price;
    private int discountRate;
    private int discountPrice;
    private int originalPrice;
    private LocalDateTime createdAt;
    private Long brandId;
}
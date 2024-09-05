package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductDetailResponseVo {

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
}

package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponseVo {
    private String productUUID;
    private String sourceUrl;
    private String brandName;
    private String productName;
    private int price;
}
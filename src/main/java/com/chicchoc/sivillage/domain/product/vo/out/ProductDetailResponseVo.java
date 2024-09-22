package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDetailResponseVo {
    private Long id;

    private String productDetailUrl;
}

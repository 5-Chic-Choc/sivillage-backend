package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOptionResponseVo {
    private String productOptionUuid;
    private Long productId;
    private Long sizeId;
    private Long colorId;
    private Long etcOptionId;
    private String saleStatus;
    private Integer price;
    private Integer discountRate;
    private Integer discountPrice;
}

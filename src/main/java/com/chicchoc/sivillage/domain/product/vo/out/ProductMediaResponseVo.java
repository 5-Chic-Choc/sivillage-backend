package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductMediaResponseVo {
    private Long id;
    private String productOptionUuid;
    private Long mediaId;
    private int mediaOrder;
}
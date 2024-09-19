package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfoResponseVo {
    private Long id;

    private String kind;

    private String value;

    private boolean isFiltered;
}

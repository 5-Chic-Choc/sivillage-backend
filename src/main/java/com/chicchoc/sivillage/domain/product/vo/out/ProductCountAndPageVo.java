package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCountAndPageVo {

    private Long totalCount;

    private Integer totalPages;

}

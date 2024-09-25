package com.chicchoc.sivillage.domain.category.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCategoryResponseVo {
    private Long id;
    private Long productId;
    private Long categoryId;
}

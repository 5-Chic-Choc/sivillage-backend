package com.chicchoc.sivillage.domain.category.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseVo {
    private Long id;
    private String name;
    private Integer depth;
}
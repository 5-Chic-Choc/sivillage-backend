package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColorResponseVo {
    private Long id;
    private String name;
    private String value;
}
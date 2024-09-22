package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductHashtagResponseVo {
    private Long id;

    private String hashtagContent;
}

package com.chicchoc.sivillage.domain.brand.vo.out;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandMediaResponseVo {
    private Long mediaId;
    private int mediaOrder;
}

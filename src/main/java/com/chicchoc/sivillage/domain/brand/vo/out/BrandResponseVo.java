package com.chicchoc.sivillage.domain.brand.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandResponseVo {
    private Long id;
    private String name;
    private String logoUrl;
}

package com.chicchoc.sivillage.domain.brand.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BrandResponseVo {
    private List<BrandResponseVo> brands;
    private Long id;
    private String name;
    private String logoUrl;
}

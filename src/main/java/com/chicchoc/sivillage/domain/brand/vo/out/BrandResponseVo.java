package com.chicchoc.sivillage.domain.brand.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandResponseVo {
    private String brandUuid;
    private String name;
    private String nameKo;
    private String logoUrl;
    private String brandListType;
    private String brandIndexLetter;
}

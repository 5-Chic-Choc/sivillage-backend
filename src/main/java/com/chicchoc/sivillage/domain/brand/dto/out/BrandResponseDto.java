package com.chicchoc.sivillage.domain.brand.dto.out;

import com.chicchoc.sivillage.domain.brand.vo.out.BrandResponseVo;
import lombok.Builder;

@Builder
public class BrandResponseDto {
    private String brandUuid;
    private String name;
    private String logoUrl;

    public BrandResponseVo toResponseVo() {
        return BrandResponseVo.builder()
                .brandUuid(brandUuid)
                .name(name)
                .logoUrl(logoUrl)
                .build();
    }
}
package com.chicchoc.sivillage.domain.brand.dto.out;

import com.chicchoc.sivillage.domain.brand.vo.out.BrandResponseVo;
import lombok.Builder;

@Builder
public class BrandResponseDto {
    private Long id;
    private String name;
    private String logoUrl;

    public BrandResponseVo toResponseVo() {
        return BrandResponseVo.builder()
                .id(id)
                .name(name)
                .logoUrl(logoUrl)
                .build();
    }
}
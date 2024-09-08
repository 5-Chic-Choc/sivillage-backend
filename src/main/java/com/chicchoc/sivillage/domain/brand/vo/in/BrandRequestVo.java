package com.chicchoc.sivillage.domain.brand.vo.in;

import com.chicchoc.sivillage.domain.brand.dto.in.BrandRequestDto;
import lombok.Getter;

@Getter
public class BrandRequestVo {

    private String name;
    private String logoUrl;

    public BrandRequestDto toDto() {
        return BrandRequestDto.builder()
                .name(name)
                .logoUrl(logoUrl)
                .build();
    }
}

package com.chicchoc.sivillage.domain.brand.vo.in;

import com.chicchoc.sivillage.domain.brand.dto.in.BrandRequestDto;
import lombok.Getter;

@Getter
public class BrandRequestVo {

    private String name;
    private String logoUrl;
    private String brandListType;
    private String brandIndexLetter;

    public BrandRequestDto toDto() {
        return BrandRequestDto.builder()
                .name(name)
                .logoUrl(logoUrl)
                .brandListType(brandListType)
                .brandIndexLetter(brandIndexLetter)
                .build();
    }
}

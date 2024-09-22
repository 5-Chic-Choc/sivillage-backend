package com.chicchoc.sivillage.domain.brand.dto.out;

import com.chicchoc.sivillage.domain.brand.vo.out.BrandResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandResponseDto {
    private String brandUuid;
    private String name;
    private String nameKo;
    private String logoUrl;
    private String brandListType;
    private String brandIndexLetter;

    public BrandResponseVo toResponseVo() {
        return BrandResponseVo.builder()
                .brandUuid(brandUuid)
                .name(name)
                .nameKo(nameKo)
                .logoUrl(logoUrl)
                .brandListType(brandListType)
                .brandIndexLetter(brandIndexLetter)
                .build();
    }
}
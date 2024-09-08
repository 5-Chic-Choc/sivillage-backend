package com.chicchoc.sivillage.domain.brand.dto.in;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BrandRequestDto {

    private String brandUuid;
    private String name;
    private String logoUrl;

    public Brand toEntity(String brandUuid) {
        return Brand.builder()
                .brandUuid(brandUuid)
                .name(name)
                .logoUrl(logoUrl)
                .build();
    }
}

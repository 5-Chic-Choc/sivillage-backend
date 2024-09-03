package com.chicchoc.sivillage.domain.brand.dto.in;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BrandRequestDto {

    private Long id;
    private String name;
    private String logoUrl;

    public Brand toEntity() {
        return Brand.builder()
                .id(id)
                .name(name)
                .logoUrl(logoUrl)
                .build();
    }

    @Builder
    public BrandRequestDto(Long id, String name, String logoUrl) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
    }
}

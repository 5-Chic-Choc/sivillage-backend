package com.chicchoc.sivillage.global.data.dto.brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDataRequestDto {

    private String brandListType;
    private String brandIndexLetter;
    private String brandName;
    private String brandNameKo;
    private String category;
}
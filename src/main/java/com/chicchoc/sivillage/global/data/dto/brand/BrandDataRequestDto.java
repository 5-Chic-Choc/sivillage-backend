package com.chicchoc.sivillage.global.data.dto.brand;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDataRequestDto {

    @JsonProperty("brand_list_type")
    private String brandListType;

    @JsonProperty("brand_index_letter")
    private String brandIndexLetter;

    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("brand_name_ko")
    private String brandNameKo;

    @JsonProperty("ctg_no")
    private String category;
}
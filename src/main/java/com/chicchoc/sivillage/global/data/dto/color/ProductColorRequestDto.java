package com.chicchoc.sivillage.global.data.dto.color;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductColorRequestDto {

    @JsonProperty("product_code")
    private String productCode;

    @JsonProperty("color_value")
    private String colorValue;

}

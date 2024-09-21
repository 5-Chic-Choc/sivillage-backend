package com.chicchoc.sivillage.global.data.dto.color;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductColorRequestDto {

    private String productCode;
    private String colorValue;

}

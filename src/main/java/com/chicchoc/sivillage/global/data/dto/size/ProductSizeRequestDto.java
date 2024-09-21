package com.chicchoc.sivillage.global.data.dto.size;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSizeRequestDto {

    private String productCode;
    private String sizeValue;

}

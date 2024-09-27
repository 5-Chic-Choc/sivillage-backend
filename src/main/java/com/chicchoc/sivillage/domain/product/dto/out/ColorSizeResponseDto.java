package com.chicchoc.sivillage.domain.product.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColorSizeResponseDto {

    private ColorResponseDto color;
    private List<SizeResponseDto> sizes;

}

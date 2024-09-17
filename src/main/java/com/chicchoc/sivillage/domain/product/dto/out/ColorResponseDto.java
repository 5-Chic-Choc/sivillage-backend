package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.domain.Color;
import com.chicchoc.sivillage.domain.product.vo.out.ColorResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColorResponseDto {
    private Long id;
    private String name;
    private String value;

    public static ColorResponseDto fromEntity(Color color) {
        return ColorResponseDto.builder()
                .id(color.getId())
                .name(color.getName())
                .value(color.getValue())
                .build();
    }

    public ColorResponseVo toVo() {
        return ColorResponseVo.builder()
                .id(this.id)
                .name(this.name)
                .value(this.value)
                .build();
    }
}
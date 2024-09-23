package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.domain.Size;
import com.chicchoc.sivillage.domain.product.vo.out.SizeResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SizeResponseDto {
    private Long id;
    private String name;
    private String value;

    public static SizeResponseDto fromEntity(Size size) {
        return SizeResponseDto.builder()
                .id(size.getId())
                .name(size.getName())
                .value(size.getValue())
                .build();
    }

    public SizeResponseVo toVo() {
        return SizeResponseVo.builder()
                .id(this.id)
                .name(this.name)
                .value(this.value)
                .build();
    }
}
package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.domain.EtcOption;
import com.chicchoc.sivillage.domain.product.vo.out.EtcOptionResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EtcOptionResponseDto {
    private Long id;
    private String name;

    public static EtcOptionResponseDto fromEntity(EtcOption etcOption) {
        return EtcOptionResponseDto.builder()
                .id(etcOption.getId())
                .name(etcOption.getName())
                .build();
    }

    public EtcOptionResponseVo toVo() {
        return EtcOptionResponseVo.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
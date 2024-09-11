package com.chicchoc.sivillage.domain.brand.dto.out;

import com.chicchoc.sivillage.domain.brand.vo.out.BrandMediaResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrandMediaResponseDto {
    private Long mediaId;
    private int mediaOrder;

    public BrandMediaResponseVo toResponseVo() {
        return BrandMediaResponseVo.builder()
                .mediaId(mediaId)
                .mediaOrder(mediaOrder)
                .build();
    }
}

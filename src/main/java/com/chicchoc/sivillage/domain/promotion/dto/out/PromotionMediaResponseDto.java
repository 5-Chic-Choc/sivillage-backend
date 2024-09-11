package com.chicchoc.sivillage.domain.promotion.dto.out;

import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionMediaResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromotionMediaResponseDto {

    private Long mediaId;

    private int mediaOrder;

    public PromotionMediaResponseVo toResponseVo() {
        return PromotionMediaResponseVo.builder()
                .mediaId(mediaId)
                .mediaOrder(mediaOrder)
                .build();
    }
}

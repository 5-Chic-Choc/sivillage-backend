package com.chicchoc.sivillage.domain.promotion.dto.out;

import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionHashtagResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromotionHashtagResponseDto {
    private String hashtagContent;

    public PromotionHashtagResponseVo toResponseVo() {
        return PromotionHashtagResponseVo.builder()
                .hashtagContent(hashtagContent)
                .build();
    }
}

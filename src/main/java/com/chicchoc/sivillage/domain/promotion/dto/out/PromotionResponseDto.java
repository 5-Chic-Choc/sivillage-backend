package com.chicchoc.sivillage.domain.promotion.dto.out;

import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionResponseVo;
import lombok.Builder;

@Builder
public class PromotionResponseDto {
    private String promotionUuid;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String promotionDetailUrl;

    public PromotionResponseVo toResponseVo() {
        return PromotionResponseVo.builder()
                .promotionUuid(promotionUuid)
                .title(title)
                .description(description)
                .thumbnailUrl(thumbnailUrl)
                .promotionDetailUrl(promotionDetailUrl)
                .build();
    }
}

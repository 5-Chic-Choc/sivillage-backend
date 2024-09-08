package com.chicchoc.sivillage.domain.promotion.dto.out;

import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionResponseVo;
import lombok.Builder;

import java.util.List;

@Builder
public class PromotionResponseDto {
    private String promotionUuid;
    private String title;
    private String description;
    private String thumbnailUrl;
    private List<String> hahtagContent;

    public PromotionResponseVo toResponseVo() {
        return PromotionResponseVo.builder()
                .promotionUuid(promotionUuid)
                .title(title)
                .description(description)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}

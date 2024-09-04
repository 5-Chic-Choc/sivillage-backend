package com.chicchoc.sivillage.domain.promotion.dto.out;

import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionResponseVo;
import lombok.Builder;

@Builder
public class PromotionResponseDto {
    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;

    public PromotionResponseVo toResponseVo() {
        return PromotionResponseVo.builder()
                .id(id)
                .title(title)
                .description(description)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}

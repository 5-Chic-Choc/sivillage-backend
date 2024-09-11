package com.chicchoc.sivillage.domain.promotion.vo.in;

import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionRequestDto;
import lombok.Getter;

@Getter
public class PromotionRequestVo {
    private String title;
    private String description;
    private String thumbnailUrl;

    public PromotionRequestDto toDto() {
        return PromotionRequestDto.builder()
                .title(title)
                .description(description)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}

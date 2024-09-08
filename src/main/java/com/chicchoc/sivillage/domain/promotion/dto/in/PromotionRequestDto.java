package com.chicchoc.sivillage.domain.promotion.dto.in;

import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromotionRequestDto {

    private String title;
    private String description;
    private String promotionDetailUrl;
    private String thumbnailUrl;

    public Promotion toEntity(String promotionUuid) {
        return Promotion.builder()
                .promotionUuid(promotionUuid)
                .title(title)
                .description(description)
                .promotionDetailUrl(promotionDetailUrl)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}

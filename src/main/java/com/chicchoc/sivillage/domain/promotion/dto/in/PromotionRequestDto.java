package com.chicchoc.sivillage.domain.promotion.dto.in;

import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromotionRequestDto {
    private Long id;

    public Promotion toEntity() {
        return Promotion.builder()
                .id(id)
                .build();
    }
}

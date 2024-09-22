package com.chicchoc.sivillage.domain.promotion.vo.out;

import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromotionResponseVo {
    private String promotionUuid;
    private String title;
    private String description;
    private String thumbnailUrl;
}

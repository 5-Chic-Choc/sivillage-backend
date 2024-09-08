package com.chicchoc.sivillage.domain.promotion.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PromotionResponseVo {
    private String promotionUuid;
    private String title;
    private String description;
    private String thumbnailUrl;
}

package com.chicchoc.sivillage.domain.promotion.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PromotionResponseVo {
    private List<PromotionResponseVo> promotions;
    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
}

package com.chicchoc.sivillage.domain.promotion.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromotionProductResponseVo {
    private String productUuid;
    private String promotionType;
}

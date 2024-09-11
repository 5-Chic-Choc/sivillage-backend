package com.chicchoc.sivillage.domain.promotion.dto.out;

import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionBenefitResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromotionBenefitResponseDto {
    private String benefitContent;

    public PromotionBenefitResponseVo toResponseVo() {
        return PromotionBenefitResponseVo.builder()
                .benefitContent(benefitContent)
                .build();
    }
}

package com.chicchoc.sivillage.domain.promotion.dto.out;

import com.chicchoc.sivillage.domain.promotion.domain.PromotionProduct;
import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionProductResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromotionProductResponseDto {
    private String productUuid;
    private String promotionType;

    public PromotionProductResponseVo toVo() {
        return PromotionProductResponseVo.builder()
                .productUuid(productUuid)
                .promotionType(promotionType)
                .build();
    }

    public static PromotionProductResponseDto fromEntity(PromotionProduct promotionProduct) {
        return PromotionProductResponseDto.builder()
                .productUuid(promotionProduct.getProductUuid())
                .promotionType(promotionProduct.getPromotionType())
                .build();
    }
}

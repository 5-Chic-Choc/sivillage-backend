package com.chicchoc.sivillage.domain.promotion.application;

import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionRequestDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionHashtagResponseDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionResponseDto;

import java.util.List;

public interface PromotionService {

    void addPromotion(PromotionRequestDto promotionRequestDto);

    List<PromotionResponseDto> findAllPromotions();

    void updatePromotion(String promotionUuid, PromotionRequestDto promotionRequestDto);

    List<PromotionHashtagResponseDto> findPromotionHashtags(String promotionUuid);
}

package com.chicchoc.sivillage.domain.promotion.application;

import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionResponseDto;

import java.util.List;

public interface PromotionService {
    List<PromotionResponseDto> getPromotions();
}

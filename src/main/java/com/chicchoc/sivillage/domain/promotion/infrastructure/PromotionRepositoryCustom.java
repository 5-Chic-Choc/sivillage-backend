package com.chicchoc.sivillage.domain.promotion.infrastructure;

import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionFilterRequestDto;

import java.util.List;

public interface PromotionRepositoryCustom {
    List<Promotion> findFilteredPromotions(PromotionFilterRequestDto dto, int offset);
}
package com.chicchoc.sivillage.domain.promotion.application;

import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionResponseDto;
import com.chicchoc.sivillage.domain.promotion.infrastructure.PromotionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    public List<PromotionResponseDto> getPromotions() {
        List<Promotion> promotions = promotionRepository.findAll();

        return promotions.stream()
                .map(promotion -> PromotionResponseDto.builder()
                        .id(promotion.getId())
                        .title(promotion.getTitle())
                        .description(promotion.getDescription())
                        .thumbnailUrl(promotion.getThumbnailUrl())
                        .build())
                .toList();
    }
}

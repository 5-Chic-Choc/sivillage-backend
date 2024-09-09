package com.chicchoc.sivillage.domain.promotion.application;

import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionRequestDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionHashtagResponseDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionResponseDto;
import com.chicchoc.sivillage.domain.promotion.infrastructure.PromotionHashtagRepository;
import com.chicchoc.sivillage.domain.promotion.infrastructure.PromotionRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final NanoIdGenerator nanoIdGenerator;
    private final PromotionRepository promotionRepository;
    private final PromotionHashtagRepository promotionHashtagRepository;

    @Override
    @Transactional
    public void addPromotion(PromotionRequestDto promotionRequestDto) {

        String promotionUuid = nanoIdGenerator.generateNanoId();

        promotionRepository.save(promotionRequestDto.toEntity(promotionUuid));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponseDto> findAllPromotions() {

        List<Promotion> promotions = promotionRepository.findAll();

        return promotions.stream()
                .map(promotion -> PromotionResponseDto.builder()
                        .promotionUuid(promotion.getPromotionUuid())
                        .title(promotion.getTitle())
                        .description(promotion.getDescription())
                        .thumbnailUrl(promotion.getThumbnailUrl())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public void updatePromotion(String promotionUuid, PromotionRequestDto promotionRequestDto) {
        Promotion promotion = promotionRepository.findByPromotionUuid(promotionUuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로모션이 존재하지 않습니다."));

        promotion.updatePromotion(promotionRequestDto.getTitle(), promotionRequestDto.getDescription(),
                promotionRequestDto.getPromotionDetailUrl(), promotionRequestDto.getThumbnailUrl());

        promotionRepository.save(promotion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionHashtagResponseDto> findPromotionHashtags(String promotionUuid) {
        List<PromotionHashtagResponseDto> promotionHashtagResponseDtos = promotionHashtagRepository
                .findByPromotionUuid(promotionUuid).stream()
                .map(promotionHashtag -> PromotionHashtagResponseDto.builder()
                        .hashtagContent(promotionHashtag.getHashtagContent())
                        .build())
                .toList();

        return promotionHashtagResponseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public PromotionResponseDto findPromotion(String promotionUuid) {
        Promotion promotion = promotionRepository.findByPromotionUuid(promotionUuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로모션이 존재하지 않습니다."));

        return PromotionResponseDto.builder()
                .promotionUuid(promotion.getPromotionUuid())
                .title(promotion.getTitle())
                .description(promotion.getDescription())
                .thumbnailUrl(promotion.getThumbnailUrl())
                .promotionDetailUrl(promotion.getPromotionDetailUrl())
                .build();
    }
}

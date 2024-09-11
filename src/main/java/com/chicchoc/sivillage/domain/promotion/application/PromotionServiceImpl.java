package com.chicchoc.sivillage.domain.promotion.application;

import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionRequestDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionBenefitResponseDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionMediaResponseDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionResponseDto;
import com.chicchoc.sivillage.domain.promotion.infrastructure.PromotionBenefitRepository;
import com.chicchoc.sivillage.domain.promotion.infrastructure.PromotionMediaRepository;
import com.chicchoc.sivillage.domain.promotion.infrastructure.PromotionRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.error.exception.BaseException;
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
    private final PromotionBenefitRepository promotionBenefitRepository;
    private final PromotionMediaRepository promotionMediaRepository;

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
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PROMOTION));

        promotion.updatePromotion(promotionRequestDto.getTitle(), promotionRequestDto.getDescription(),
                promotionRequestDto.getThumbnailUrl());

        promotionRepository.save(promotion);
    }

    @Override
    @Transactional(readOnly = true)
    public PromotionResponseDto findPromotion(String promotionUuid) {
        Promotion promotion = promotionRepository.findByPromotionUuid(promotionUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PROMOTION));

        return PromotionResponseDto.builder()
                .promotionUuid(promotion.getPromotionUuid())
                .title(promotion.getTitle())
                .description(promotion.getDescription())
                .thumbnailUrl(promotion.getThumbnailUrl())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionBenefitResponseDto> findPromotionBenefits(String promotionUuid) {

        promotionRepository.findByPromotionUuid(promotionUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PROMOTION));

        List<PromotionBenefitResponseDto> promotionBenefitResponseDtos = promotionBenefitRepository
                .findByPromotionUuid(promotionUuid).stream()
                .map(promotionBenefit -> PromotionBenefitResponseDto.builder()
                        .benefitContent(promotionBenefit.getBenefitContent())
                        .build())
                .toList();

        return promotionBenefitResponseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionMediaResponseDto> findPromotionMedias(String promotionUuid) {

        promotionRepository.findByPromotionUuid(promotionUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PROMOTION));

        List<PromotionMediaResponseDto> promotionMediaResponseDtos = promotionMediaRepository
                .findByPromotionUuid(promotionUuid).stream()
                .map(promotionMedia -> PromotionMediaResponseDto.builder()
                        .mediaId(promotionMedia.getMediaId())
                        .mediaOrder(promotionMedia.getMediaOrder())
                        .build())
                .toList();

        return promotionMediaResponseDtos;
    }
}

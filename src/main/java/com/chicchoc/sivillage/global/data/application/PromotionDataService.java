package com.chicchoc.sivillage.global.data.application;

import com.chicchoc.sivillage.domain.media.domain.Media;
import com.chicchoc.sivillage.domain.media.infrastructure.MediaRepository;
import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
import com.chicchoc.sivillage.domain.promotion.domain.PromotionBenefit;
import com.chicchoc.sivillage.domain.promotion.domain.PromotionMedia;
import com.chicchoc.sivillage.domain.promotion.infrastructure.PromotionBenefitRepository;
import com.chicchoc.sivillage.domain.promotion.infrastructure.PromotionMediaRepository;
import com.chicchoc.sivillage.domain.promotion.infrastructure.PromotionProductRepository;
import com.chicchoc.sivillage.domain.promotion.infrastructure.PromotionRepository;
import com.chicchoc.sivillage.global.common.aop.annotation.TimeAop;
import com.chicchoc.sivillage.global.data.dto.promotion.PromotionDataRequestDto;
import com.chicchoc.sivillage.global.data.dto.promotion.PromotionDataRequestDto.CategoryProductDto;
import com.chicchoc.sivillage.global.data.dto.promotion.PromotionDataRequestDto.ProductDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PromotionDataService {

    private final PromotionRepository promotionRepository;
    private final PromotionProductRepository promotionProductRepository;
    private final PromotionMediaRepository promotionMediaRepository;
    private final MediaRepository mediaRepository;
    private final PromotionBenefitRepository promotionBenefitRepository;

    /**
     * 프로모션 데이터 저장(프로모션, 프로모션 혜택, 프로모션 미디어, 미디어, 프로모션-상품).
     *
     * @param dtos 프로모션 데이터
     */


    @TimeAop
    @Transactional
    public void savePromotionData(List<PromotionDataRequestDto> dtos) {

        // todo : FE와 API 연결 후, 가지고 있는 제품 데이터와 promotion 데이터를 비교하여, 존재하는 제품만 저장하도록 수정

        for (PromotionDataRequestDto dto : dtos) {
            if (checkNull(dto)) {
                log.error("dto is null : {}", dto);
                continue;
            }

            System.out.println("dto = " + dto);
            String promotionUuid = dto.getId();

            // 프로모션 저장
            if (promotionRepository.findByPromotionUuid(promotionUuid).isPresent()) {
                log.info("이미 존재하는 프로모션. dto = " + dto);
                continue;
            }
            Promotion promotion = promotionRepository.save(dto.toEntity());

            // 프로모션 혜택 저장
            // 혜택 데이터가 없을 경우 저장
            List<PromotionBenefit> benefits = promotionBenefitRepository.findByPromotionUuid(promotionUuid);

            if (benefits.isEmpty()) {
                for (String tag : dto.getTags()) {
                    benefits.add(PromotionBenefit.builder()
                            .promotionUuid(promotionUuid)
                            .benefitContent(tag)
                            .build());
                }

                promotionBenefitRepository.saveAll(benefits);
            }

            // 미디어 및 프로모션 미디어 저장
            saveImages(dto.getPromotionImages(), promotion.getPromotionUuid());

            // 프로모션 상품 저장( 프로모션 내 분류별 상품들)
            for (CategoryProductDto categoryProductDto : dto.getCategoryProducts()) {
                for (ProductDto productDto : categoryProductDto.getProducts()) {
                    promotionProductRepository.save(
                            productDto.toPromotionProductEntity(promotionUuid, categoryProductDto.getCategoryName()));
                }
            }
        }
    }

    private boolean checkNull(PromotionDataRequestDto dto) {
        try {
            // 어차피 데이터는 많으니, 유효한 데이터만 저장하면 됨
            if (dto.getId() == null || dto.getThumbnailImg() == null || dto.getBrand() == null || dto.getTitle() == null
                    || dto.getDescription() == null || dto.getDescription().trim().isEmpty() || dto.getTags() == null
                    || dto.getPromotionImages() == null
                    || dto.getPromotionImages().isEmpty() || dto.getCategoryProducts() == null) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private void saveImages(List<String> dto, String promotionUuid) {

        List<PromotionMedia> promotionMediaList = new ArrayList<>();
        List<Media> mediaList = new ArrayList<>();
        Map<String, Media> imageMediaMap = new HashMap<>();

        // Step 2-1. 미디어 저장
        for (String image : dto) {
            Media media = Media.builder()
                    .mediaUrl(image)
                    .mediaType("img")
                    .build();
            mediaList.add(media);
            imageMediaMap.put(image, media);
        }
        mediaRepository.saveAll(mediaList);

        // Step 2-2. 프로모션 미디어 저장
        for (Map.Entry<String, Media> entry : imageMediaMap.entrySet()) {

            promotionMediaList.add(PromotionMedia.builder()
                    .promotionUuid(promotionUuid)
                    .mediaId(entry.getValue().getId())
                    .build());
        }
        promotionMediaRepository.saveAll(promotionMediaList);
    }
}

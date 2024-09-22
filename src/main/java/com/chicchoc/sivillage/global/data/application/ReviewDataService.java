package com.chicchoc.sivillage.global.data.application;

import com.chicchoc.sivillage.domain.media.domain.Media;
import com.chicchoc.sivillage.domain.media.infrastructure.MediaRepository;
import com.chicchoc.sivillage.domain.review.domain.ReviewMedia;
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewMediaRepository;
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewRepository;
import com.chicchoc.sivillage.global.common.aop.annotation.TimeAop;
import com.chicchoc.sivillage.global.data.dto.product.ProductDataRequestDto.ImageDto;
import com.chicchoc.sivillage.global.data.dto.review.ReviewDataRequestDto;
import jakarta.persistence.EntityManager;
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
public class ReviewDataService {


    private final ReviewRepository reviewRepository;
    private final ReviewMediaRepository reviewMediaRepository;
    private final MediaRepository mediaRepository;


    /**
     * 리뷰 데이터 데이터가 담긴 List(Json 파일)을 순회하며 총 3개의 테이블에 데이터 저장하는 메서드(리뷰, 리뷰 미디어, 미디어)를 호출.
     *
     * @param dtos : 리뷰 데이터를 받는 DTO 리스트
     */

    @TimeAop
    @Transactional
    public void saveReviewData(List<ReviewDataRequestDto> dtos) {

        for (ReviewDataRequestDto dto : dtos) {

            // 필수 데이터가 null인 경우
            if (checkNull(dto)) {
                log.info("필수 데이터가 null입니다. dto = " + dto);
                continue;
            }

            // Step 1. 리뷰 저장
            if (reviewRepository.findByReviewUuid(dto.getProductEvalNo()).isPresent()) {
                log.info("이미 존재하는 리뷰. dto = " + dto);
                continue;
            }
            reviewRepository.save(dto.toEntity());

            // Step 2. 리뷰 미디어 및 미디어 저장
            if (dto.getImages() != null && !dto.getImages().isEmpty()) {
                saveImages(dto.getImages(), dto.getProductEvalNo());
            }
        }
    }

    private boolean checkNull(ReviewDataRequestDto dto) {
        try {
            // 필수 데이터가 null인 경우
            if (dto.getProductEvalNo() == null || dto.getProductCode() == null || dto.getReviewerName() == null
                    || dto.getReviewText() == null || dto.getReviewText().trim().isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private void saveImages(List<ImageDto> dto, String reviewUuid) {

        List<ReviewMedia> reviewMediaList = new ArrayList<>();
        List<Media> mediaList = new ArrayList<>();
        Map<ImageDto, Media> imageMediaMap = new HashMap<>();

        // Step 2-1. 미디어 저장
        for (ImageDto image : dto) {
            Media media = Media.builder()
                    .mediaUrl(image.getSrc())
                    .mediaType("img")
                    .build();
            mediaList.add(media);
            imageMediaMap.put(image, media);
        }
        mediaRepository.saveAll(mediaList);

        // Step 2-2. 리뷰 미디어 저장
        for (Map.Entry<ImageDto, Media> entry : imageMediaMap.entrySet()) {

            reviewMediaList.add(ReviewMedia.builder()
                    .reviewUuid(reviewUuid)
                    .mediaId(entry.getValue().getId())
                    .mediaOrder(entry.getKey().getIdx())
                    .build());
        }
        reviewMediaRepository.saveAll(reviewMediaList);
    }
}
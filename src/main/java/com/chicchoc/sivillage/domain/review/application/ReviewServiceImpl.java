package com.chicchoc.sivillage.domain.review.application;

import com.chicchoc.sivillage.domain.media.domain.Media;
import com.chicchoc.sivillage.domain.media.infrastructure.MediaRepository;
import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.domain.review.domain.ReviewMedia;
import com.chicchoc.sivillage.domain.review.dto.in.ReviewMediaRequestDto;
import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;
import com.chicchoc.sivillage.domain.review.dto.out.ReviewMediaResponseDto;
import com.chicchoc.sivillage.domain.review.dto.out.ReviewResponseDto;
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewMediaRepository;
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.chicchoc.sivillage.global.infra.application.S3Service;
import com.chicchoc.sivillage.global.infra.dto.MediaDto;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMediaRepository reviewMediaRepository;
    private final MediaRepository mediaRepository;
    private final NanoIdGenerator nanoIdGenerator;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Override
    public void addReview(String userUuid, ReviewRequestDto reviewRequestDto, List<MultipartFile> fileList) {

        Member member = memberRepository.findByUuid(userUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        Review review = reviewRequestDto.toEntity(nanoIdGenerator.generateNanoId(), userUuid, member.getEmail());

        Review savedReview = reviewRepository.save(review);

        // 여기까지 단순 리뷰 저장 로직
        // 아래는 이미지 S3및 DB 저장

        if (!fileList.isEmpty()) {
            int imgOrder = 1;
            for (MultipartFile file : fileList) {
                try {
                    MediaDto mediaDto = s3Service.uploadFile(file, "review");
                    Media media = mediaDto.toEntity();
                    Media savedMedia = mediaRepository.save(media);
                    log.info("@@@@@@ ,{}", savedMedia);
                    ReviewMediaRequestDto reviewMediaRequestDto = ReviewMediaRequestDto.builder()
                            .reviewUuid(savedReview.getReviewUuid())
                            .mediaId(savedMedia.getId())
                            .mediaOrder(imgOrder++)
                            .build();
                    reviewMediaRepository.save(reviewMediaRequestDto.toEntity());
                } catch (Exception e) {
                    throw new BaseException(BaseResponseStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewByProductUuid(String productUuid) {

        List<Review> reviewList = reviewRepository.findByProductUuid(productUuid);

        List<String> reviewUuidList = reviewList.stream()
                .map(Review::getReviewUuid)
                .toList();

        List<ReviewMedia> reviewMediaList = reviewMediaRepository.findByReviewUuidIn(reviewUuidList);

        List<ReviewMediaResponseDto> reviewMediaResponseDtoList = reviewMediaList.stream()
                .map(ReviewMediaResponseDto::fromEntity)
                .toList();

        Map<String, List<ReviewMediaResponseDto>> reviewUuidToMediaMap = reviewMediaResponseDtoList.stream()
                .collect(Collectors.groupingBy(ReviewMediaResponseDto::getReviewUuid));

        return ReviewResponseDto.fromEntity(reviewList, reviewUuidToMediaMap).stream()
                .sorted(Comparator.comparingInt(ReviewResponseDto::getLikedCnt).reversed()).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewByUserUuid(String userUuid) {

        List<Review> reviewList = reviewRepository.findByUserUuid(userUuid);

        List<String> reviewUuidList = reviewList.stream()
                .map(Review::getReviewUuid)
                .toList();

        List<ReviewMedia> reviewMediaList = reviewMediaRepository.findByReviewUuidIn(reviewUuidList);

        List<ReviewMediaResponseDto> reviewMediaResponseDtoList = reviewMediaList.stream()
                .map(ReviewMediaResponseDto::fromEntity)
                .toList();

        Map<String, List<ReviewMediaResponseDto>> reviewUuidToMediaMap = reviewMediaResponseDtoList.stream()
                .collect(Collectors.groupingBy(ReviewMediaResponseDto::getReviewUuid));

        return ReviewResponseDto.fromEntity(reviewList, reviewUuidToMediaMap);
    }

    @Override
    public void deleteReview(String reviewUuid) {
//        Review review = reviewRepository.findByReviewUuid(reviewUuid).orElseThrow(() -> new BaseException(BaseResponseStatus.valueOf()));
    }
}
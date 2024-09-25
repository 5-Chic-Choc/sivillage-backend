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
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewListRepositoryCustom;
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewMediaRepository;
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.common.utils.CursorPage;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.chicchoc.sivillage.global.infra.application.S3Service;
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
    private final ReviewListRepositoryCustom reviewListRepository;
    private final S3Service s3Service;

    @Override
    public void addReview(String userUuid, ReviewRequestDto reviewRequestDto, List<MultipartFile> fileList) {

        Member member = memberRepository.findByUuid(userUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        Review savedReview = reviewRepository.save(
                reviewRequestDto.toEntity(NanoIdGenerator.generateNanoId(), userUuid, member.getEmail()));

        if (!fileList.isEmpty()) {
            int imgOrder = 1;
            for (MultipartFile file : fileList) {
                try {
                    Media savedMedia = mediaRepository.save(s3Service.uploadFile(file, "review").toEntity());

                    reviewMediaRepository.save(
                            ReviewMediaRequestDto.builder()
                                    .reviewUuid(savedReview.getReviewUuid())
                                    .mediaId(savedMedia.getId())
                                    .mediaOrder(imgOrder++)
                                    .build().toEntity()
                    );
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

        Map<String, List<ReviewMediaResponseDto>> reviewUuidToMediaMap = getReviews(reviewList);

        return ReviewResponseDto.fromEntity(reviewList, reviewUuidToMediaMap).stream()
                .sorted(Comparator.comparingInt(ReviewResponseDto::getLikedCnt).reversed()).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewByUserUuid(String userUuid) {

        List<Review> reviewList = reviewRepository.findByUserUuid(userUuid);

        Map<String, List<ReviewMediaResponseDto>> reviewUuidToMediaMap = getReviews(reviewList);

        return ReviewResponseDto.fromEntity(reviewList, reviewUuidToMediaMap);
    }

    @Override
    public void deleteReview(String reviewUuid) {
        reviewRepository.delete(reviewRepository.findByReviewUuid(reviewUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_REVIEW)));

        reviewMediaRepository.deleteAll(reviewMediaRepository.findByReviewUuid(reviewUuid));
    }

    @Override
    public CursorPage<String> getAllReviews(String productUuid, String userUuid, Long lastId, Integer pageSize,
            Integer page) {

        return reviewListRepository.getReviewList(productUuid, userUuid, lastId, pageSize, page);
    }

    private Map<String, List<ReviewMediaResponseDto>> getReviews(List<Review> reviewList) {

        List<ReviewMedia> reviewMediaList = reviewMediaRepository.findByReviewUuidIn(reviewList.stream()
                .map(Review::getReviewUuid)
                .toList());

        return reviewMediaList.stream()
                .map(ReviewMediaResponseDto::fromEntity)
                .toList().stream()
                .collect(Collectors.groupingBy(ReviewMediaResponseDto::getReviewUuid));
    }
}
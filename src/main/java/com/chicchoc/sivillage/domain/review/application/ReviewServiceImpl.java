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
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewRepositoryCustom;
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
    private final MemberRepository memberRepository;
    private final ReviewRepositoryCustom reviewListRepository;
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
    public void deleteReview(String reviewUuid) {
        reviewRepository.delete(reviewRepository.findByReviewUuid(reviewUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_REVIEW)));

        reviewMediaRepository.deleteAll(reviewMediaRepository.findByReviewUuid(reviewUuid));
    }

    @Override
    public CursorPage<String> getAllReviews(String userUuid, Long lastId, Integer pageSize,
            Integer page) {

        return reviewListRepository.getReviewListByUserUuid(userUuid, lastId, pageSize, page);
    }

    @Override
    public CursorPage<String> getReviewByProductUuid(String productUuid, Long lastId, Integer pageSize, Integer page) {
        return reviewListRepository.getReviewListByProductUuid(productUuid, lastId, pageSize, page);
    }

    @Override
    public ReviewResponseDto getReview(String reviewUuid) {

        return ReviewResponseDto.fromEntity(reviewRepository.findByReviewUuid(reviewUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_REVIEW)));
    }

    @Override
    public List<ReviewMediaResponseDto> getReviewMedia(String reviewUuid) {
        return reviewMediaRepository.findByReviewUuid(reviewUuid)
                .stream().map(ReviewMediaResponseDto::fromEntity).toList();
    }
}
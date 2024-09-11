package com.chicchoc.sivillage.domain.review.application;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;
import com.chicchoc.sivillage.domain.review.dto.out.ReviewResponseDto;
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.jwt.util.JwtUtil;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final NanoIdGenerator nanoIdGenerator;
    private final MemberRepository memberRepository;
    @Override
    public void addReview(ReviewRequestDto reviewRequestDto) {
        String userUuid = JwtUtil.getUserUuid();
        String reviewUuid = nanoIdGenerator.generateNanoId();
        Optional<Member> member = memberRepository.findByUuid(userUuid);
        String userEmail = member.get().getEmail();

        reviewRepository.save(reviewRequestDto.toEntity(reviewUuid, userUuid, userEmail));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewByProductUuid(String productUuid) {
        List<Review> reviewList = reviewRepository.findByProductUuid(productUuid);

        return commonDtoStream(reviewList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewByUserUuid() {
        String userUuid = JwtUtil.getUserUuid();
        List<Review> reviewList = reviewRepository.findByUserUuid(userUuid);

        return commonDtoStream(reviewList);
    }

    public List<ReviewResponseDto> commonDtoStream(List<Review> reviewList) {
        return reviewList.stream()
                .map(review -> ReviewResponseDto.builder()
                        .reviewUuid(review.getReviewUuid())
                        .productUuid(review.getProductUuid())
                        .userUuid(review.getUserUuid())
                        .sizeName(review.getSizeName())
                        .colorValue(review.getColorValue())
                        .reviewContent(review.getReviewContent())
                        .starPoint(review.getStarPoint())
                        .likedCnt(review.getLikedCnt())
                        .reviewerEmail(review.getReviewerEmail())
                        .reviewRateType1(review.getReviewRateType1())
                        .reviewRateText1(review.getReviewRateText1())
                        .reviewRateType2(review.getReviewRateType2())
                        .reviewRateText2(review.getReviewRateText2())
                        .reviewRateType3(review.getReviewRateType3())
                        .reviewRateText3(review.getReviewRateText3())
                        .build())
                .toList();
    }
}
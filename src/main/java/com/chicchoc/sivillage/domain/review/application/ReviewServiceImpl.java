package com.chicchoc.sivillage.domain.review.application;

import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;
import com.chicchoc.sivillage.domain.review.dto.out.ReviewResponseDto;
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewRepository;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    public void addReview(ReviewRequestDto reviewRequestDto) {
        // TODO UserID 받아오는 로직
        Long userId = null;
        reviewRepository.save(reviewRequestDto.toEntity(userId));
    }

    @Override
    public List<ReviewResponseDto> getReviewByProductId(Long productId) {
        List<Review> reviewListByProductId;
        try {
            reviewListByProductId = reviewRepository.findByProductId(productId);
        } catch (Exception e) {
            throw new IllegalArgumentException("해당 리뷰를 찾을 수 없음.");
        }
        return reviewListByProductId.stream()
                .map(review -> ReviewResponseDto.builder()
                        .Id(review.getId())
                        .productId(review.getProductId())
                        .size(review.getSize())
                        .info(review.getInfo())
                        .info(review.getInfo())
                        .rate(review.getRate())
                        .createAt(review.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public ReviewResponseVo getReviewByUserId(Long userId) {
        Optional<Review> reviewListByUserId;
        try {
            reviewListByUserId = reviewRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("해당 사용자의 리뷰를 찾을 수 없음.");
        }
        System.out.println(reviewListByUserId);
        return null;
    }
}

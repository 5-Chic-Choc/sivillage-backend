package com.chicchoc.sivillage.domain.review.application;

import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;
import com.chicchoc.sivillage.domain.review.dto.out.ReviewResponseDto;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import java.util.List;

public interface ReviewService {
  void addReview(ReviewRequestDto reviewRequestDto);
  List<ReviewResponseDto> getReviewByProductId(Long productId);
  ReviewResponseVo getReviewByUserId(Long userId);
}

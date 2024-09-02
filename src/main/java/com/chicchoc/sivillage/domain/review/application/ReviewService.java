package com.chicchoc.sivillage.domain.review.application;

import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import java.util.List;

public interface ReviewService {
  void addReview(ReviewRequestDto reviewRequestDto);
  ReviewResponseVo getReviewById(Long id);
  ReviewResponseVo getReviewByUserId(Long userId);
}

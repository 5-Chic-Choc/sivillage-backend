package com.chicchoc.sivillage.domain.review.application;

import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;

public interface ReviewService {

    void addReview(ReviewRequestDto reviewRequestDto);
}

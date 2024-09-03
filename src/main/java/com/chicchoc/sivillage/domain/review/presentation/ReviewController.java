package com.chicchoc.sivillage.domain.review.presentation;

import com.chicchoc.sivillage.domain.review.application.ReviewService;
import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;
import com.chicchoc.sivillage.domain.review.vo.in.ReviewRequestVo;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping
  public CommonResponseEntity<Void> createReview(@RequestBody ReviewRequestVo reviewRequestVo) {
    ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder()
        .productId(reviewRequestVo.getProductId())
        .size(reviewRequestVo.getSize())
        .reviewOption(reviewRequestVo.getReviewOption())
        .info(reviewRequestVo.getInfo())
        .rate(reviewRequestVo.getRate())
        .content(reviewRequestVo.getContent())
        .build();
    reviewService.addReview(reviewRequestDto);
    return new CommonResponseEntity<>(
        HttpStatus.CREATED,
        "리뷰 작성 성공",
        null
    );
  }
}




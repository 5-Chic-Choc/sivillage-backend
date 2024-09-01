package com.chicchoc.sivillage.domain.review.application;

import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  
  @Override
  public void addReview(ReviewRequestDto reviewRequestDto) {
    // TODO UserID 받아오는 로직
    System.out.println(reviewRequestDto);
    Long userId = 132L;
    reviewRepository.save(reviewRequestDto.toEntity(userId));
  }
}

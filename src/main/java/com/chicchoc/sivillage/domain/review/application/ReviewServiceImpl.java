package com.chicchoc.sivillage.domain.review.application;

import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewRepository;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import java.util.List;
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
  public ReviewResponseVo getReviewById(Long id) {
    try{
      reviewRepository.findById(id);
    }
    catch(Exception e){
      throw new IllegalArgumentException("해당 리뷰를 찾을 수 없음.");
    }

    return null;
  }

  @Override
  public ReviewResponseVo getReviewByUserId(Long userId) {
    reviewRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다"));
    return null;
  }
}

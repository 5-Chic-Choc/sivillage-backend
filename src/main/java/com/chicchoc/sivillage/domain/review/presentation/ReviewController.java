package com.chicchoc.sivillage.domain.review.presentation;

import com.chicchoc.sivillage.domain.review.application.ReviewService;
import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;
import com.chicchoc.sivillage.domain.review.dto.out.ReviewResponseDto;
import com.chicchoc.sivillage.domain.review.vo.in.ReviewRequestVo;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public BaseResponse<Void> createReview(Authentication authentication,
            @RequestBody ReviewRequestVo reviewRequestVo) {
        ReviewRequestDto reviewRequestDto = reviewRequestVo.toDto();
        reviewService.addReview(authentication.getName(), reviewRequestDto);
        return new BaseResponse<>();
    }

    @GetMapping("/product/{productUuid}")
    public BaseResponse<List<ReviewResponseVo>> getReviewByProductUuId(
            @PathVariable("productUuid") String productUuid) {
        List<ReviewResponseDto> reviewResponseDto = reviewService.getReviewByProductUuid(productUuid);

        List<ReviewResponseVo> reviewResponseVoList = reviewResponseDto.stream()
                .map(ReviewResponseDto::toResponseVo)
                .toList();

        return new BaseResponse<>(reviewResponseVoList);
    }

    @GetMapping("/user")
    public BaseResponse<List<ReviewResponseVo>> getReviewByUserUuid() {
        List<ReviewResponseDto> reviewResponseDto = reviewService.getReviewByUserUuid();

        List<ReviewResponseVo> reviewResponseVoList = reviewResponseDto.stream()
                .map(ReviewResponseDto::toResponseVo)
                .toList();

        return new BaseResponse<>(reviewResponseVoList);
    }

}




package com.chicchoc.sivillage.domain.review.presentation;

import com.chicchoc.sivillage.domain.review.application.ReviewService;
import com.chicchoc.sivillage.domain.review.dto.out.ReviewResponseDto;
import com.chicchoc.sivillage.domain.review.vo.in.ReviewRequestVo;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public BaseResponse<Void> createReview(Authentication authentication,
            @RequestPart ReviewRequestVo reviewRequestVo,
            @RequestPart(value = "file", required = false) List<MultipartFile> fileList) {

        reviewService.addReview(authentication.getName(), reviewRequestVo.toDto(), fileList);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/product/{productUuid}")
    public BaseResponse<List<ReviewResponseVo>> getReviewByProductUuId(
            @PathVariable("productUuid") String productUuid) {

        List<ReviewResponseVo> reviewResponseVoList = reviewService.getReviewByProductUuid(productUuid).stream()
                .map(ReviewResponseDto::toResponseVo)
                .toList();

        return new BaseResponse<>(reviewResponseVoList);
    }

    @GetMapping("/user")
    public BaseResponse<List<ReviewResponseVo>> getReviewByUserUuid(Authentication authentication) {

        List<ReviewResponseVo> reviewResponseVoList = reviewService.getReviewByUserUuid(authentication.getName())
                .stream()
                .map(ReviewResponseDto::toResponseVo)
                .toList();

        return new BaseResponse<>(reviewResponseVoList);
    }

}




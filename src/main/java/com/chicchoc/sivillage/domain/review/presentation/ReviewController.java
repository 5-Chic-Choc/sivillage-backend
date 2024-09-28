package com.chicchoc.sivillage.domain.review.presentation;

import com.chicchoc.sivillage.domain.review.application.ReviewService;
import com.chicchoc.sivillage.domain.review.dto.out.ReviewMediaResponseDto;
import com.chicchoc.sivillage.domain.review.vo.in.ReviewRequestVo;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewMediaResponseVo;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.utils.CursorPage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "createReview API", description = "리뷰 생성", tags = {"리뷰"})
    @PostMapping
    public BaseResponse<Void> createReview(Authentication authentication,
                                           @RequestPart ReviewRequestVo reviewRequestVo,
                                           @RequestPart(value = "file", required = false)
                                           List<MultipartFile> fileList) {

        reviewService.addReview(authentication.getName(), reviewRequestVo.toDto(), fileList);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "getReview API", description = "리뷰 조회", tags = {"리뷰"})
    @GetMapping("/user")
    public BaseResponse<CursorPage<String>> getReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Long lastId, @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer page) {

        return new BaseResponse<>(
                reviewService.getAllReviews(userDetails.getUsername(), lastId, pageSize, page));
    }

    @Operation(summary = "getReviewMedia API", description = "리뷰 미디어 조회", tags = {"리뷰"})
    @GetMapping("/productUuid")
    public BaseResponse<CursorPage<String>> getReviewMediaByProductUuid(
            @RequestParam(required = false) String productUuid,
            @RequestParam(required = false) Long lastId, @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer page) {

        return new BaseResponse<>(reviewService.getReviewByProductUuid(productUuid, lastId, pageSize, page));
    }

    @Operation(summary = "getReview API", description = "리뷰 단 건 조회", tags = {"리뷰"})
    @GetMapping("/{reviewUuid}")
    public BaseResponse<ReviewResponseVo> getReviewByReviewUuid(@PathVariable("reviewUuid") String reviewUuid) {
        return new BaseResponse<>(reviewService.getReview(reviewUuid).toResponseVo());
    }

    @Operation(summary = "getReviewMedia API", description = "리뷰 미디어 조회", tags = {"리뷰"})
    @GetMapping("/Media/{reviewUuid}")
    public BaseResponse<List<ReviewMediaResponseVo>> getReviewMediaByReviewUuid(
            @PathVariable("reviewUuid") String reviewUuid) {
        return new BaseResponse<>(
                reviewService.getReviewMedia(reviewUuid).stream().map(ReviewMediaResponseDto::toVo).toList());
    }

    @Operation(summary = "deleteReview API", description = "리뷰 삭제", tags = {"리뷰"})
    @DeleteMapping("/{reviewUuid}")
    public BaseResponse<Void> deleteReview(@PathVariable("reviewUuid") String reviewUuid) {
        reviewService.deleteReview(reviewUuid);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }
}
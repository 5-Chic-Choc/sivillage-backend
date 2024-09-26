package com.chicchoc.sivillage.domain.review.presentation;

import com.chicchoc.sivillage.domain.review.application.ReviewService;
import com.chicchoc.sivillage.domain.review.dto.out.ReviewMediaResponseDto;
import com.chicchoc.sivillage.domain.review.vo.in.ReviewRequestVo;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewMediaResponseVo;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.utils.CursorPage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/user")
    public BaseResponse<CursorPage<String>> getReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Long lastId, @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer page) {

        return new BaseResponse<>(
                reviewService.getAllReviews(userDetails.getUsername(), lastId, pageSize, page));
    }

    @GetMapping("/productUuid")
    public BaseResponse<CursorPage<String>> getReviewMediaByProductUuid(
            @RequestParam(required = false) String productUuid,
            @RequestParam(required = false) Long lastId, @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer page) {

        return new BaseResponse<>(reviewService.getReviewByProductUuid(productUuid, lastId, pageSize, page));
    }

    @GetMapping("/{reviewUuid}")
    public BaseResponse<ReviewResponseVo> getReviewByReviewUuid(@PathVariable("reviewUuid") String reviewUuid) {
        return new BaseResponse<>(reviewService.getReview(reviewUuid).toResponseVo());
    }

    @GetMapping("/Media/{reviewUuid}")
    public BaseResponse<List<ReviewMediaResponseVo>> getReviewMediaByReviewUuid(
            @PathVariable("reviewUuid") String reviewUuid) {
        return new BaseResponse<>(
                reviewService.getReviewMedia(reviewUuid).stream().map(ReviewMediaResponseDto::toVo).toList());
    }

    @DeleteMapping("/{reviewUuid}")
    public BaseResponse<Void> deleteReview(@PathVariable("reviewUuid") String reviewUuid) {
        reviewService.deleteReview(reviewUuid);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }
}
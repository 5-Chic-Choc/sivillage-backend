package com.chicchoc.sivillage.domain.review.dto.out;

import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private String reviewUuid;
    private String productUuid;
    private String userUuid;
    private String sizeName;
    private String colorValue;
    private String optionName;
    private String reviewContent;
    private byte starPoint;
    private Integer likedCnt;
    private String reviewerEmail;
    private String reviewRateType1;
    private String reviewRateType2;
    private String reviewRateType3;
    private String reviewRateText1;
    private String reviewRateText2;
    private String reviewRateText3;
    private LocalDateTime createdAt;

    public ReviewResponseVo toResponseVo() {
        return ReviewResponseVo.builder()
                .reviewUuid(reviewUuid)
                .productUuid(productUuid)
                .userUuid(userUuid)
                .optionName(optionName)
                .reviewContent(reviewContent)
                .starPoint(starPoint)
                .likedCnt(likedCnt)
                .reviewerEmail(reviewerEmail)
                .reviewRateType1(reviewRateType1)
                .reviewRateText1(reviewRateText1)
                .reviewRateType2(reviewRateType2)
                .reviewRateText2(reviewRateText2)
                .reviewRateType3(reviewRateType3)
                .reviewRateText3(reviewRateText3)
                .createdAt(createdAt)
                .build();
    }

    public static ReviewResponseDto fromEntity(Review review) {
        return ReviewResponseDto.builder()
                .reviewUuid(review.getReviewUuid())
                .productUuid(review.getProductUuid())
                .userUuid(review.getUserUuid())
                .reviewContent(review.getReviewContent())
                .optionName(review.getOptionName())
                .starPoint(review.getStarPoint())
                .likedCnt(review.getLikedCnt())
                .reviewerEmail(review.getReviewerEmail())
                .reviewRateType1(review.getReviewRateType1())
                .reviewRateText1(review.getReviewRateText1())
                .reviewRateType2(review.getReviewRateType2())
                .reviewRateText2(review.getReviewRateText2())
                .reviewRateType3(review.getReviewRateType3())
                .reviewRateText3(review.getReviewRateText3())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
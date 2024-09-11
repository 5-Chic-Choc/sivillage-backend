package com.chicchoc.sivillage.domain.review.dto.in;

import com.chicchoc.sivillage.domain.review.domain.Review;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewRequestDto {

    private String productUuid;
    private String sizeName;
    private String colorValue;
    private String reviewContent;
    private byte starPoint;
    private String reviewRateType1;
    private String reviewRateText1;
    private String reviewRateType2;
    private String reviewRateText2;
    private String reviewRateType3;
    private String reviewRateText3;

    public Review toEntity(String reviewUuid, String userUuid, String reviewerEmail) {
        return Review.builder()
                .reviewUuid(reviewUuid)
                .productUuid(productUuid)
                .userUuid(userUuid)
                .sizeName(sizeName)
                .colorValue(colorValue)
                .reviewContent(reviewContent)
                .starPoint(starPoint)
                .reviewerEmail(reviewerEmail)
                .reviewRateType1(reviewRateType1)
                .reviewRateText1(reviewRateText1)
                .reviewRateType2(reviewRateType2)
                .reviewRateText2(reviewRateText2)
                .reviewRateType3(reviewRateType3)
                .reviewRateText3(reviewRateText3)
                .build();
    }

}

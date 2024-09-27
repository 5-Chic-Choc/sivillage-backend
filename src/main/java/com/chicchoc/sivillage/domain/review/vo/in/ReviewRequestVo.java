package com.chicchoc.sivillage.domain.review.vo.in;

import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;
import lombok.Getter;

@Getter
public class ReviewRequestVo {

    private String productUuid;
    private String reviewContent;
    private String optionName;
    private byte starPoint;
    private String reviewRateType1;
    private String reviewRateText1;
    private String reviewRateType2;
    private String reviewRateText2;
    private String reviewRateType3;
    private String reviewRateText3;

    public ReviewRequestDto toDto() {
        return ReviewRequestDto.builder()
                .productUuid(productUuid)
                .reviewContent(reviewContent)
                .optionName(optionName)
                .starPoint(starPoint)
                .reviewRateType1(reviewRateType1)
                .reviewRateText1(reviewRateText1)
                .reviewRateType2(reviewRateType2)
                .reviewRateText2(reviewRateText2)
                .reviewRateType3(reviewRateType3)
                .reviewRateText3(reviewRateText3)
                .build();
    }
}

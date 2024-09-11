package com.chicchoc.sivillage.domain.review.dto.out;

import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.Date;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private String reviewUuid;
    private String productUuid;
    private String userUuid;
    private String sizeName;
    private String colorValue;
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

    public ReviewResponseVo toResponseVo() {
        return ReviewResponseVo.builder()
                .reviewUuid(reviewUuid)
                .productUuid(productUuid)
                .userUuid(userUuid)
                .sizeName(sizeName)
                .colorValue(colorValue)
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
                .build();
    }
}
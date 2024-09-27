package com.chicchoc.sivillage.domain.review.vo.out;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseVo {

    private String reviewUuid;
    private String productUuid;
    private String userUuid;
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
}
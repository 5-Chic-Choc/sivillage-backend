package com.chicchoc.sivillage.domain.review.vo.out;

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
    private String sizeName;
    private String colorValue;
    private String reviewContent;
    private byte starpoint;
    private Integer likedCnt;
    private String reviewerEmail;

}
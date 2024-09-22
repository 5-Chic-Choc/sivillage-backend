package com.chicchoc.sivillage.domain.review.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewMediaResponseVo {
    private String reviewUuid;
    private Long mediaId;
    private int mediaOrder;
}

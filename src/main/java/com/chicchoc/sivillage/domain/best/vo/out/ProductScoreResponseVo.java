package com.chicchoc.sivillage.domain.best.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductScoreResponseVo {

    private Integer likeCount;

    private Integer reviewCount;

    private Float starPointAverage;

    private Float totalScore;
}

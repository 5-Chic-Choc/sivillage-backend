package com.chicchoc.sivillage.domain.best.dto;

import com.chicchoc.sivillage.domain.best.domain.ProductScore;
import com.chicchoc.sivillage.domain.best.vo.out.ProductScoreResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductScoreResponseDto {

    private Integer likeCount;

    private Integer reviewCount;

    private Float starPointAverage;

    private Float totalScore;

    public ProductScoreResponseVo toVo() {
        return ProductScoreResponseVo.builder()
                .likeCount(likeCount)
                .reviewCount(reviewCount)
                .starPointAverage(starPointAverage)
                .totalScore(totalScore)
                .build();
    }

    public static ProductScoreResponseDto fromEntity(ProductScore productScore) {
        return ProductScoreResponseDto.builder()
                .likeCount(productScore.getLikeCount())
                .reviewCount(productScore.getReviewCount())
                .starPointAverage(productScore.getStarPointAverage())
                .totalScore(productScore.getTotalScore())
                .build();
    }
}

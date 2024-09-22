package com.chicchoc.sivillage.domain.best.dto;

import com.chicchoc.sivillage.domain.best.domain.ProductScore;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductScoreDto {

    private String productUuid;
    private Integer salesCount;
    private Integer likeCount;
    private Integer reviewCount;
    private Float starPointAverage;
    private Float totalScore;

    // ProductScore 엔티티로 변환하는 메서드
    public ProductScore toEntity() {
        return ProductScore.builder()
                .productUuid(productUuid)
                .salesCount(salesCount)
                .likeCount(likeCount)
                .reviewCount(reviewCount)
                .starPointAverage(starPointAverage)
                .totalScore(totalScore)
                .build();
    }

}

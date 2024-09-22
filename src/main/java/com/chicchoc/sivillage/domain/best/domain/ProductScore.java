package com.chicchoc.sivillage.domain.best.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_score")
public class ProductScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_score_id")
    private Long id;

    @Comment("상품 UUID")
    @Column(name = "product_uuid", nullable = false, length = 21)
    private String productUuid;

    @Comment("판매 수")
    @Column(name = "sales_count")
    private Integer salesCount;

    @Comment("좋아요 수")
    @Column(name = "like_count")
    private Integer likeCount;

    @Comment("리뷰 수")
    @Column(name = "review_count")
    private Integer reviewCount;

    @Comment("별점 평균")
    @Column(name = "star_point_average")
    private Float starPointAverage;

    @Comment("총점")
    @Column(name = "total_score")
    private Float totalScore;

    // 도메인 메서드를 통해 값을 변경
    public void updateScores(Integer salesCount,
                             Integer likeCount,
                             Integer reviewCount,
                             Float starPointAverage,
                             Float totalScore) {
        this.salesCount = salesCount;
        this.likeCount = likeCount;
        this.reviewCount = reviewCount;
        this.starPointAverage = starPointAverage;
        this.totalScore = totalScore;
    }
}
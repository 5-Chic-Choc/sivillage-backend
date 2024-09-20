package com.chicchoc.sivillage.domain.review.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "review_uuid", nullable = false, length = 21)
    private String reviewUuid;

    @Column(name = "product_uuid", nullable = false, length = 21)
    private String productUuid;

    @Column(name = "user_uud", nullable = false, length = 21)
    private String userUuid;

    @Column(name = "size_name", nullable = false, length = 10)
    private String sizeName;

    @Column(name = "color_value", nullable = false, length = 30)
    private String colorValue;

    @Column(name = "review_content", nullable = false, length = 500)
    private String reviewContent;

    @Column(name = "starpoint", nullable = false)
    private byte starPoint;

    @Column(name = "liked_cnt")
    private Integer likedCnt = 0;

    @Column(name = "reviewer_email", nullable = false, length = 30)
    private String reviewerEmail;

    @Column(name = "review_rate_type_1")
    private String reviewRateType1;

    @Column(name = "review_rate_text_1")
    private String reviewRateText1;

    @Column(name = "review_rate_type_2")
    private String reviewRateType2;

    @Column(name = "review_rate_text_2")
    private String reviewRateText2;

    @Column(name = "review_rate_type_3")
    private String reviewRateType3;

    @Column(name = "review_rate_text_3")
    private String reviewRateText3;

    @Builder
    public Review(String reviewUuid, String productUuid, String userUuid, String sizeName, String colorValue,
            String reviewContent, byte starPoint, String reviewerEmail, String reviewRateType1,
            String reviewRateText1, String reviewRateType2, String reviewRateText2, String reviewRateType3,
            String reviewRateText3) {
        this.reviewUuid = reviewUuid;
        this.productUuid = productUuid;
        this.userUuid = userUuid;
        this.sizeName = sizeName;
        this.colorValue = colorValue;
        this.reviewContent = reviewContent;
        this.starPoint = starPoint;
        this.reviewerEmail = reviewerEmail;
        this.reviewRateType1 = reviewRateType1;
        this.reviewRateText1 = reviewRateText1;
        this.reviewRateType2 = reviewRateType2;
        this.reviewRateText2 = reviewRateText2;
        this.reviewRateType3 = reviewRateType3;
        this.reviewRateText3 = reviewRateText3;
    }
}

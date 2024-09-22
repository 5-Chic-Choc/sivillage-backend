package com.chicchoc.sivillage.domain.review.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "review")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Comment("리뷰 UUID")
    @Column(name = "review_uuid", nullable = false, length = 21)
    private String reviewUuid;

    @Comment("상품 UUID")
    @Column(name = "product_uuid", nullable = false, length = 21)
    private String productUuid;

    @Comment("리뷰 작성자 UUID")
    @Column(name = "user_uud", nullable = false, length = 21)
    private String userUuid;

    @Comment("리뷰 작성자 이메일")
    @Column(name = "reviewer_email")
    private String reviewerEmail;

    @Comment("리뷰 내용")
    @Column(name = "review_content", nullable = false, length = 10000)
    private String reviewContent;

    @Comment("별점")
    @Column(name = "star_point", nullable = false)
    private byte starPoint;

    @Comment("좋아요 갯수")
    @Column(name = "liked_cnt")
    private Integer likedCnt;

    @Comment("사이즈명")
    @Column(name = "size_name")
    private String sizeName;

    @Comment("색상명")
    @Column(name = "color_value")
    private String colorValue;

    @Comment("옵션명")
    @Column(name = "option_name")
    private String optionName;

    @Comment("리뷰 태그 타입 1")
    @Column(name = "review_rate_type_1", length = 30)
    private String reviewRateType1;

    @Comment("리뷰 태그 타입 2")
    @Column(name = "review_rate_type_2", length = 30)
    private String reviewRateType2;

    @Comment("리뷰 태그 타입 3")
    @Column(name = "review_rate_type_3", length = 30)
    private String reviewRateType3;

    @Comment("리뷰 태그 텍스트 1")
    @Column(name = "review_rate_text_1", length = 50)
    private String reviewRateText1;

    @Comment("리뷰 태그 텍스트 2")
    @Column(name = "review_rate_text_2", length = 50)
    private String reviewRateText2;

    @Comment("리뷰 태그 텍스트 3")
    @Column(name = "review_rate_text_3", length = 50)
    private String reviewRateText3;
}

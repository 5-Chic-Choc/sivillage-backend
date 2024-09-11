package com.chicchoc.sivillage.domain.review.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import lombok.NoArgsConstructor;

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

    @Column(name = "review_uuid", nullable = false, length = 21)
    private String reviewUuid;

    @Column(name = "product_uuid", nullable = false, length = 21)
    private String productUuid;

    @Column(name = "user_uud", nullable = false, length = 21)
    private String userUuid;

    @Column(name = "size_name", nullable = false)
    private String sizeName;

    @Column(name = "color_value", nullable = false)
    private String colorValue;

    @Column(name = "review_content", nullable = false)
    private String reviewContent;

    @Column(name = "starpoint", nullable = false)
    private byte starpoint;

    @Column(name = "liked_cnt")
    private Integer likedCnt;

    @Column(name = "reviewer_email")
    private String reviewerEmail;
    
}

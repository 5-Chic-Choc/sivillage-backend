package com.chicchoc.sivillage.domain.promotion.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long id;

    @Comment("프로모션 UUID")
    @Column(nullable = false, length = 21)
    private String promotionUuid;

    @Comment("프로모션 제목")
    @Column(nullable = false, length = 100)
    private String title;

    @Comment("프로모션 간단 설명")
    @Column(nullable = false, length = 255)
    private String description;

    @Comment("프로모션 상세 URL")
    @Column(nullable = false, length = 2000)
    private String promotionDetailUrl;

    @Comment("프로모션 썸네일 이미지 URL")
    @Column(nullable = false, length = 2000)
    private String thumbnailUrl;

    public void updatePromotion(String title, String description, String promotionDetailUrl, String thumbnailUrl) {
        this.title = title;
        this.description = description;
        this.promotionDetailUrl = promotionDetailUrl;
        this.thumbnailUrl = thumbnailUrl;
    }
}
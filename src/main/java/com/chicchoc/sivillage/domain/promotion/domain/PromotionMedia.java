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
public class PromotionMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_media_id")
    private Long id;

    @Comment("프로모션 UUID")
    @Column(nullable = false, length = 21)
    private String promotionUuid;

    @Comment("미디어 ID")
    @Column(nullable = false)
    private Long mediaId;

    @Comment("미디어 순서")
    private int mediaOrder;
}

package com.chicchoc.sivillage.domain.promotion.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class PromotionProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Comment("상품 UUID")
    @Column(nullable = false, length = 21)
    private String productUuid;

    @Comment("프로모션 타입")
    @Column(nullable = false, length = 50)
    private String promotionType;
}

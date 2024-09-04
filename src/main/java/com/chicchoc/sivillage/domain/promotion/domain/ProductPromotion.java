package com.chicchoc.sivillage.domain.promotion.domain;

import com.chicchoc.sivillage.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class ProductPromotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Comment("프로모션 타입")
    @Column(nullable = false, length = 50)
    private String promotionType;
}

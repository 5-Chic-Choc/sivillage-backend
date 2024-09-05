package com.chicchoc.sivillage.domain.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Comment("색상 ID")
    @Column(nullable = false)
    private Long colorId;

    @Comment("상품 상세 URL")
    @Column(nullable = false, length = 2000)
    private String productDetailUrl;
}

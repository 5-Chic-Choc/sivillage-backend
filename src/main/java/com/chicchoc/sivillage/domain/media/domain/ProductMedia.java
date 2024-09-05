package com.chicchoc.sivillage.domain.media.domain;

import com.chicchoc.sivillage.domain.product.domain.ProductOrderOption;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class ProductMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_media_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_order_option_id")
    private ProductOrderOption productOrderOption;

    @Comment("제품 이미지 순서")
    @Column(nullable = false)
    private int mediaOrder;

    @Comment("미디어 ID")
    private Long mediaId;
}

package com.chicchoc.sivillage.domain.category.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_id")
    private Long id;

    @Comment("상품 id")
    @Column(nullable = false)
    private Long productId;

    @Comment("카테고리 id")
    @Column(nullable = false)
    private Long categoryId;
}

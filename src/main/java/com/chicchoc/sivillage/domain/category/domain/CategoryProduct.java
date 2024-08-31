package com.chicchoc.sivillage.domain.category.domain;

import com.chicchoc.sivillage.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CategoryProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}

package com.chicchoc.sivillage.domain.brand.domain;


import com.chicchoc.sivillage.domain.product.domain.ProductOrderOption;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class StockPerBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_per_branch_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_order_option_id")
    private ProductOrderOption productOrderOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_branch_id")
    private BrandBranch brandBranch;

    @Comment("재고량")
    @Column(nullable = false)
    private int amount;
}

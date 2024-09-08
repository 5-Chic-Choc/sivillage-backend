package com.chicchoc.sivillage.domain.brand.domain;


import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class BrandStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_stock_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_branch_id")
    private BrandBranch brandBranch;

    @Comment("제품 옵션 ID")
    @Column(nullable = false)
    private Long productOptionId;

    @Comment("재고량")
    @Column(nullable = false)
    private int amount;
}

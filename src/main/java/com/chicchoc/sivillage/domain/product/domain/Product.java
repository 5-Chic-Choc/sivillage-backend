package com.chicchoc.sivillage.domain.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Comment("상품 uuid")
    @Column(nullable = false, length = 20, name = "product_uuid")
    private String productUuid;

    @Comment("상품 이름")
    @Column(nullable = false, length = 30)
    private String productName;

    @Comment("상품 등록일")
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Comment("상품 브랜드")
    @Column(nullable = true)
    private Long brandId;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductOrderOption> productOrderOptions;

}

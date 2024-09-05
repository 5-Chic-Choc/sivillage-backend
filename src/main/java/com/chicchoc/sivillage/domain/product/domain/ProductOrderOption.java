package com.chicchoc.sivillage.domain.product.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class ProductOrderOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_order_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etc_option_id")
    private EtcOption etcOption;

    @Comment("판매 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @Comment("가격")
    @Column(nullable = false)
    private int price;

    @Comment("할인율")
    @Column(nullable = true)
    private int discountRate;

    @Comment("할인가격")
    @Column(nullable = true)
    private int discountPrice;

    @Comment("상품옵션코드")
    @Column(nullable = false, length = 20)
    private String productOptionCode;
}

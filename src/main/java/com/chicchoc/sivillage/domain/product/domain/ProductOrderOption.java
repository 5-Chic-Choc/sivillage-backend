package com.chicchoc.sivillage.domain.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class ProductOrderOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_order_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etc_option_id")
    private EtcOption etc_option;

    @Comment("판매 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus sale_status;

    @Comment("가격")
    @Column(nullable = false)
    private int price;

    @Comment("할인율")
    @Column(nullable = true)
    private int discount_rate;

    @Comment("할인가격")
    @Column(nullable = true)
    private int discount_price;

    @Comment("상품옵션코드")
    @Column(nullable = false, length = 20)
    private String product_option_code;
}

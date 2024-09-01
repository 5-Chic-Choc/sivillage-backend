package com.chicchoc.sivillage.domain.cart.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Comment("상품 주문 옵션 ID")
    @Column(name = "product_order_option_id")
    private Long productOrderOptionId;

    @Comment("주문 수량")
    @Column(nullable = false)
    private int amount;
}

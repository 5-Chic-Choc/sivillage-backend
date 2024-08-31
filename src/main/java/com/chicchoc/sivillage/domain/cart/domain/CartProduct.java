package com.chicchoc.sivillage.domain.cart.domain;

import jakarta.persistence.*;
import lombok.Getter;

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

    private Long productOrderOptionId;

    private int amount;
}

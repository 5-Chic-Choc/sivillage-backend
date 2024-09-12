package com.chicchoc.sivillage.domain.cart.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @Comment("장바구니 제품 uuid")
    @Column(name = "cart_product_uuid", nullable = false, length = 21)
    private String cartProductUuid;

    @Comment("장바구니 uuid")
    @Column(name = "cart_uuid", nullable = false, length = 21)
    private String cartUuid;

    @Comment("제품 옵션 uuid")
    @Column(name = "product_option_uuid")
    private String productOptionUuid;

    @Comment("주문 수량")
    @Column(nullable = false)
    private int amount;
}

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
    @Column(name = "cart_product_uuid")
    private String cartProductUuid;

    @Comment("장바구니 uuid")
    @Column(name = "cart_uuid")
    private String cartUuid;

    @Comment("상품 주문 옵션 ID")
    @Column(name = "product_order_option_id")
    private Long productOrderOptionId;

    @Comment("주문 수량")
    @Column(nullable = false)
    private int amount;
}

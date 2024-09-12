package com.chicchoc.sivillage.domain.cart.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Column(name = "amount", nullable = false)
    private int amount;

    @Comment("제품 선택 여부")
    @Column(name = "is_selected", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isSelected;
}

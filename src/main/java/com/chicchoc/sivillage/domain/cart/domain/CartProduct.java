package com.chicchoc.sivillage.domain.cart.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "cart_product")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @Comment("제품 장바구니 Uuid")
    @Column(name = "cart_product_uuid", nullable = false, length = 21)
    private String cartProductUuid;

    @Comment("장바구니 Uuid")
    @Column(name = "cart_uuid", nullable = false, length = 21)
    private String cartUuid;

    @Comment("제품 옵션 uuid")
    @Column(name = "product_option_uuid", nullable = false, length = 21)
    private String productOptionUuid;

    @Comment("주문 수량")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Comment("선택 여부")
    @Builder.Default
    @Column(name = "is_selected")
    private Boolean isSelected = true;
}

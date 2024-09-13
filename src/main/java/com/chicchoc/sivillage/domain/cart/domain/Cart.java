package com.chicchoc.sivillage.domain.cart.domain;

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
@Table(name = "cart")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Comment("사용자 Uuid")
    @Column(name = "user_uuid", nullable = false, length = 21)
    private String userUuid;

    @Comment("제품 옵션 uuid")
    @Column(name = "product_option_uuid", nullable = false, length = 21)
    private String productOptionUuid;

    @Comment("주문 수량")
    @Column(name = "amount", nullable = false)
    private int amount;

    @Comment("선택 여부")
    @Builder.Default
    @Column(name = "is_selected")
    private Boolean isSelected = false;
}

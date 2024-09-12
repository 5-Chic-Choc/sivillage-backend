package com.chicchoc.sivillage.domain.cart.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "cart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Comment("장바구니 Uuid")
    @Column(name = "cart_uuid", nullable = false, length = 21)
    private String cartUuid;

    @Comment("사용자 고유 코드")
    @Column(name = "user_uuid", nullable = false, length = 21)
    private String userUuid;
}

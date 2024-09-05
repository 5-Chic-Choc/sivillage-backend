package com.chicchoc.sivillage.domain.cart.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Comment("회원 분류")
    @Column(nullable = false)
    private boolean isSigned;

    @Comment("사용자 고유 코드")
    @Column(name = "user_uuid", nullable = false, length = 50)
    private String userUuid;

    @Comment("장바구니 이름")
    @Column(nullable = true, length = 30)
    private String name;
}

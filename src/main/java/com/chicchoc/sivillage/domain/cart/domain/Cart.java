package com.chicchoc.sivillage.domain.cart.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private Long id;

    private boolean isSigned;

    private String userCode;

    private String name;
}

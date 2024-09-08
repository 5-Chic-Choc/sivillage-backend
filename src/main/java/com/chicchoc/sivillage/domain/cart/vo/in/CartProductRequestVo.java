package com.chicchoc.sivillage.domain.cart.vo.in;

import lombok.Getter;

@Getter
public class CartProductRequestVo {
    private String cartUuid;
    private Long productOrderOptionId;
    private int amount;
}

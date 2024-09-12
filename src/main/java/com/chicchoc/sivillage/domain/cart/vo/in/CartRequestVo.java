package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;

public class CartRequestVo {
    private String productOptionUuid;
    private int amount;

    public CartRequestDto toDto(){
        return CartRequestDto.builder()
                .productOptionUuid(productOptionUuid)
                .amount(amount)
                .build();
    }
}

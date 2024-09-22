package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartProductOptionUpdateRequestDto;

public class CartProductOptionUpdateRequestVo {
    private String productOptionUuid;
    private int quantity;

    public CartProductOptionUpdateRequestDto toDto(String cartProductUuid) {
        return CartProductOptionUpdateRequestDto.builder()
                .cartProductUuid(cartProductUuid)
                .productOptionUuid(productOptionUuid)
                .quantity(quantity)
                .build();
    }
}

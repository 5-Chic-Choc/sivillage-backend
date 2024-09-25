package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartUpdateRequestDto;
import lombok.Getter;

@Getter
public class CartUpdateRequestVo {

    private String productOptionUuid;
    private int quantity;

    public CartUpdateRequestDto toDto(String userUuid, String cartUuid) {
        return CartUpdateRequestDto.builder()
                .userUuid(userUuid)
                .cartUuid(cartUuid)
                .productOptionUuid(productOptionUuid)
                .quantity(quantity)
                .build();
    }
}

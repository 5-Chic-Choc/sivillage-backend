package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import lombok.Getter;

@Getter
public class CartRequestVo {

    private String productUuid;
    private String productOptionUuid;
    private int quantity;

    public CartRequestDto toDto(String userUuid) {
        return CartRequestDto.builder()
                .userUuid(userUuid)
                .productUuid(productUuid)
                .productOptionUuid(productOptionUuid)
                .quantity(quantity)
                .build();
    }
}

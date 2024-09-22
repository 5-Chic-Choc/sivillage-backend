package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import lombok.Getter;

@Getter
public class CartRequestVo {

    private String productOptionUuid;
    private Integer amount;

    public CartRequestDto toDto(String userUuid) {
        return CartRequestDto.builder()
                .userUuid(userUuid)
                .productOptionUuid(productOptionUuid)
                .amount(amount)
                .build();
    }
}

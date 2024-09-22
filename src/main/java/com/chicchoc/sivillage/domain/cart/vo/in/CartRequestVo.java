package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import lombok.Getter;

@Getter
public class CartRequestVo {

    private String cartName;

    public CartRequestDto toDto(String userUuid) {
        return CartRequestDto.builder()
                .userUuid(userUuid)
                .cartName(cartName)
                .build();
    }
}

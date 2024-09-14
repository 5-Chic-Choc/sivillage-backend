package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartDeleteRequestDto;
import lombok.Getter;

@Getter
public class CartDeleteRequestVo {
    private String cartUuid;

    public CartDeleteRequestDto toDto() {
        return CartDeleteRequestDto.builder()
                .cartUuid(cartUuid)
                .build();
    }
}

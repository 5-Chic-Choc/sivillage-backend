package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartProductDeleteRequestDto;
import lombok.Getter;

@Getter
public class CartProductDeleteRequestVo {

    private String cartUuid;

    public CartProductDeleteRequestDto toDto() {
        return CartProductDeleteRequestDto.builder()
                .cartUuid(cartUuid)
                .build();
    }
}

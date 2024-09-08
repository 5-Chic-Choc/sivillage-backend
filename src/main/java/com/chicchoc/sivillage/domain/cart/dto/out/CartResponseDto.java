package com.chicchoc.sivillage.domain.cart.dto.out;

import com.chicchoc.sivillage.domain.cart.vo.out.CartResponseVo;
import lombok.Builder;

public class CartResponseDto {
    private String cartUuid;

    public CartResponseVo toCartResponseVo() {
        return CartResponseVo.builder()
                .cartUuid(cartUuid)
                .build();
    }

    @Builder
    public CartResponseDto(String cartUuid) {
        this.cartUuid = cartUuid;
    }
}

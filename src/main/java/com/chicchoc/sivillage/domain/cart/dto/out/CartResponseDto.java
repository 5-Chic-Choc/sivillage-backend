package com.chicchoc.sivillage.domain.cart.dto.out;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.domain.cart.vo.out.CartResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {

    private String cartUuid;
    private String cartName;

    public CartResponseVo toVo() {
        return CartResponseVo.builder()
                .cartUuid(cartUuid)
                .cartName(cartName)
                .build();
    }

    public static CartResponseDto fromDto(Cart cart){
        return CartResponseDto.builder()
                .cartUuid(cart.getCartUuid())
                .cartName(cart.getCartName())
                .build();
    }
}

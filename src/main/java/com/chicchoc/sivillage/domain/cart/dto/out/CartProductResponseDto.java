package com.chicchoc.sivillage.domain.cart.dto.out;

import com.chicchoc.sivillage.domain.cart.vo.out.CartProductResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductResponseDto {

    private String cartProductUuid;
    private String productOptionUuid;
    private int amount;

    public CartProductResponseVo toCartProductResponseVo() {
        return CartProductResponseVo.builder()
                .cartProductUuid(cartProductUuid)
                .productOptionUuid(productOptionUuid)
                .amount(amount)
                .build();
    }
}

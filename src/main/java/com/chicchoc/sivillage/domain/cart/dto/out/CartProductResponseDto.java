package com.chicchoc.sivillage.domain.cart.dto.out;

import com.chicchoc.sivillage.domain.cart.vo.out.CartProductResponseVo;
import com.chicchoc.sivillage.domain.cart.vo.out.CartResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductResponseDto {

    private String cartProductUuid;
    private Long productOrderOptionId;
    private int amount;

    public CartProductResponseVo toCartProductResponseVo() {
        return CartProductResponseVo.builder()
                .cartProductUuid(cartProductUuid)
                .productOrderOptionId(productOrderOptionId)
                .amount(amount)
                .build();
    }
}

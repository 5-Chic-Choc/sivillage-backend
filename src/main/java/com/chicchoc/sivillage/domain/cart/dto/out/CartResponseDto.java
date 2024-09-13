package com.chicchoc.sivillage.domain.cart.dto.out;

import com.chicchoc.sivillage.domain.cart.vo.out.CartResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {

    private String cartUuid;
    private String productOptionUuid;
    private int amount;
    private Boolean isSelected;

    public CartResponseVo toVo() {
        return CartResponseVo.builder()
                .cartUuid(cartUuid)
                .productOptionUuid(productOptionUuid)
                .amount(amount)
                .isSelected(isSelected).build();
    }
}

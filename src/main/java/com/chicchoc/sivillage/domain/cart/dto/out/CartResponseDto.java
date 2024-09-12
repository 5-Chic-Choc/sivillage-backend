package com.chicchoc.sivillage.domain.cart.dto.out;

import com.chicchoc.sivillage.domain.cart.vo.out.CartResponseVo;

public class CartResponseDto {
    private String productOptionUuid;
    private int amount;
    private Boolean isSelected;

    public CartResponseVo toVo(){
        return CartResponseVo.builder()
                .productOptionUuid(productOptionUuid)
                .amount(amount)
                .isSelected(isSelected).build();
    }
}

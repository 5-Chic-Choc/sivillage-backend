package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartProductRequestDto;
import lombok.Getter;

@Getter
public class CartProductRequestVo {
    private String cartUuid;
    private String productOptionUuid;
    private int quantity;
    private Boolean isSelected;

    public CartProductRequestDto toDto(){
        return CartProductRequestDto.builder()
                .cartUuid(cartUuid)
                .productOptionUuid(productOptionUuid)
                .quantity(quantity)
                .isSelected(isSelected)
                .build();
    }
}

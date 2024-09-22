package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartProductStatusUpdateRequestDto;
import lombok.Getter;

@Getter
public class CartProductStatusUpdateRequestVo {

    private int quantity;
    private Boolean isSelected;

    public CartProductStatusUpdateRequestDto toDto(String cartProductUuid){
        return CartProductStatusUpdateRequestDto.builder()
                .cartProductUuid(cartProductUuid)
                .quantity(quantity)
                .isSelected(isSelected)
                .build();
    }

}

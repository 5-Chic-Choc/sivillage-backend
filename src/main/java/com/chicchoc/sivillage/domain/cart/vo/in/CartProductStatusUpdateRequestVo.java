package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartProductStatusUpdateRequestDto;
import lombok.Getter;

@Getter
public class CartProductStatusUpdateRequestVo {

    private String cartProductUuid;
    private int quantity;
    private Boolean isSelected;

    public CartProductStatusUpdateRequestDto toDto(String cartUuid){
        return CartProductStatusUpdateRequestDto.builder()
                .cartUuid(cartUuid)
                .cartProductUuid(cartProductUuid)
                .quantity(quantity)
                .isSelected(isSelected)
                .build();
    }

}

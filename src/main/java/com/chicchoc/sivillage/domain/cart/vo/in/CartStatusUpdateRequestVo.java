package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartStatusUpdateRequestDto;
import lombok.Getter;

@Getter
public class CartStatusUpdateRequestVo {

    private String cartUuid;
    private Integer amount;
    private Boolean isSelected;

    public CartStatusUpdateRequestDto toDto() {
        return CartStatusUpdateRequestDto.builder()
                .cartUuid(cartUuid)
                .amount(amount)
                .isSelected(isSelected)
                .build();
    }
}

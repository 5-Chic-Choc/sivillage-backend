package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartStatusUpdateDto;
import lombok.Getter;

@Getter
public class CartStatusUpdateVo {

    private String cartUuid;
    private Integer amount;
    private Boolean isSelected;

    public CartStatusUpdateDto toDto() {
        return CartStatusUpdateDto.builder()
                .cartUuid(cartUuid)
                .amount(amount)
                .isSelected(isSelected)
                .build();
    }
}

package com.chicchoc.sivillage.domain.cart.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartStatusUpdateRequestDto {

    private String cartUuid;
    private Integer amount;
    private Boolean isSelected;

    @Builder
    public CartStatusUpdateRequestDto(String cartUuid, Integer amount, Boolean isSelected) {
        this.cartUuid = cartUuid;
        this.amount = amount;
        this.isSelected = isSelected;
    }
}

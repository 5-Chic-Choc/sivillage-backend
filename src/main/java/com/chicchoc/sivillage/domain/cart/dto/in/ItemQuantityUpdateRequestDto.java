package com.chicchoc.sivillage.domain.cart.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemQuantityUpdateRequestDto {

    private String cartUuid;
    private int quantity;

    @Builder
    public ItemQuantityUpdateRequestDto(String cartUuid, int quantity) {
        this.cartUuid = cartUuid;
        this.quantity = quantity;
    }
}

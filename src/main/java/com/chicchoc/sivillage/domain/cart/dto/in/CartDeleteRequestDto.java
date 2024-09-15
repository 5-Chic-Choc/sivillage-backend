package com.chicchoc.sivillage.domain.cart.dto.in;

import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
public class CartDeleteRequestDto {
    private String cartUuid;

    @Builder
    public CartDeleteRequestDto(String cartUuid) {
        this.cartUuid = cartUuid;
    }
}

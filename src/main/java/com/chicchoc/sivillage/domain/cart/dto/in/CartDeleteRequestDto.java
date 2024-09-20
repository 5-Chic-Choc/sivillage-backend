package com.chicchoc.sivillage.domain.cart.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class CartDeleteRequestDto {
    private String cartUuid;

    @Builder
    public CartDeleteRequestDto(String cartUuid) {
        this.cartUuid = cartUuid;
    }
}

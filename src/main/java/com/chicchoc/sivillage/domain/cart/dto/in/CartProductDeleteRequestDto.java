package com.chicchoc.sivillage.domain.cart.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class CartProductDeleteRequestDto {

    private String cartProductUuid;

    @Builder
    public CartProductDeleteRequestDto(String cartProductUuid) {
        this.cartProductUuid = cartProductUuid;
    }
}

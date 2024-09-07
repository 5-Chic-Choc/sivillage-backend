package com.chicchoc.sivillage.domain.cart.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductResponseDto {

    private String cartProductUuid;
    private Long productOrderOptionId;
    private int amount;
}

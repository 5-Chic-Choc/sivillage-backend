package com.chicchoc.sivillage.domain.cart.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class CartProductResponseDto {

    private Long cartProductUuid;
    private Long productOrderOptionId;
    private int amount;
}

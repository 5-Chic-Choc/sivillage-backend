package com.chicchoc.sivillage.domain.cart.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartUpdateRequestDto {
    private String productOptionUuid;
    private Integer amount;

    @Builder
    public CartUpdateRequestDto(String productOptionUuid, int amount) {
        this.productOptionUuid = productOptionUuid;
        this.amount = amount;
    }
}

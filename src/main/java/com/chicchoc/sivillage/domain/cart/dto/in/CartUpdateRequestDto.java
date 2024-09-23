package com.chicchoc.sivillage.domain.cart.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartUpdateRequestDto {
    private String userUuid;
    private String cartUuid;
    private String productOptionUuid;
    private int quantity;

    @Builder
    public CartUpdateRequestDto(String userUuid, String cartUuid, String productOptionUuid, int quantity) {
        this.userUuid = userUuid;
        this.cartUuid = cartUuid;
        this.productOptionUuid = productOptionUuid;
        this.quantity = quantity;
    }
}

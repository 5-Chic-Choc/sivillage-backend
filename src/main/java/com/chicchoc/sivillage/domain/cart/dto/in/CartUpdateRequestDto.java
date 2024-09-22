package com.chicchoc.sivillage.domain.cart.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateRequestDto {

    private String cartUuid;
    private String cartName;

//    @Builder
//    public CartUpdateRequestDto(String cartUuid, String productOptionUuid, int amount) {
//        this.cartUuid = cartUuid;
//        this.productOptionUuid = productOptionUuid;
//        this.amount = amount;
//    }
}

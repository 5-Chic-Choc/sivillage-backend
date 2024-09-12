package com.chicchoc.sivillage.domain.cart.dto.in;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.domain.cart.domain.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductRequestDto {

    private String cartUuid;
    private String productOptionUuid;
    private int amount;

    public CartProduct toEntity(String cartProductUuid) {
        return CartProduct.builder()
                .cartProductUuid(cartProductUuid)
                .cartUuid(cartUuid)
                .productOptionUuid(productOptionUuid)
                .amount(amount).build();
    }
}

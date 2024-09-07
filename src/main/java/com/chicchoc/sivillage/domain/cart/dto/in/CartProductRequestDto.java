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

    private Long productOrderOptionId;
    private int amount;

    public CartProduct toEntity(String cartProductUuid, String cartUuid) {
        return CartProduct.builder()
                .cartProductUuid(cartProductUuid)
                .cartUuid(cartUuid)
                .productOrderOptionId(productOrderOptionId)
                .amount(amount).build();
    }
}

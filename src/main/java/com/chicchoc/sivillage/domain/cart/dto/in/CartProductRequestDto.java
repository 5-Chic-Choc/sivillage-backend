package com.chicchoc.sivillage.domain.cart.dto.in;

import com.chicchoc.sivillage.domain.cart.domain.CartProduct;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartProductRequestDto {

    private String cartProductUuid;
    private String cartUuid;
    private String productOptionUuid;
    private int quantity;
    private Boolean isSelected;

    @Builder
    public CartProductRequestDto(String cartUuid, String productOptionUuid, int quantity, Boolean isSelected) {
        this.cartProductUuid = NanoIdGenerator.generateNanoId();
        this.cartUuid = cartUuid;
        this.productOptionUuid = productOptionUuid;
        this.quantity = quantity;
        this.isSelected = isSelected;
    }

    public CartProduct toEntity() {
        return CartProduct.builder()
                .cartProductUuid(cartProductUuid)
                .cartUuid(cartUuid)
                .productOptionUuid(productOptionUuid)
                .quantity(quantity)
                .isSelected(isSelected)
                .build();
    }
}

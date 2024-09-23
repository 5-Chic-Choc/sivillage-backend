package com.chicchoc.sivillage.domain.cart.dto.in;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartRequestDto {

    private String cartUuid;
    private String userUuid;
    private String productUuid;
    private String productOptionUuid;
    private int quantity;

    public Cart toEntity() {
        return Cart.builder()
                .cartUuid(cartUuid)
                .userUuid(userUuid)
                .productUuid(productUuid)
                .productOptionUuid(productOptionUuid)
                .quantity(quantity)
                .isSelected(false)
                .build();
    }

    @Builder
    public CartRequestDto(String userUuid, String productOptionUuid, int quantity, String productUuid) {
        this.cartUuid = NanoIdGenerator.generateNanoId();
        this.userUuid = userUuid;
        this.productUuid = productUuid;
        this.productOptionUuid = productOptionUuid;
        this.quantity = quantity;
    }
}

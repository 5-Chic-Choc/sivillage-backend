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
    private String productOptionUuid;
    private Integer amount;

    public Cart toEntity() {
        return Cart.builder()
                .cartUuid(cartUuid)
                .userUuid(userUuid)
                .productOptionUuid(productOptionUuid)
                .amount(amount)
                .isSelected(false)
                .build();
    }

    @Builder
    public CartRequestDto(String userUuid, String productOptionUuid, Integer amount) {
        this.cartUuid = NanoIdGenerator.generateNanoId();
        this.userUuid = userUuid;
        this.productOptionUuid = productOptionUuid;
        this.amount = amount;
    }
}

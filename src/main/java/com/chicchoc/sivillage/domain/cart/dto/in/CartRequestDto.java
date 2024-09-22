package com.chicchoc.sivillage.domain.cart.dto.in;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartRequestDto {

    private String userUuid;
    private String cartUuid;
    private String cartName;

    @Builder
    public CartRequestDto(String userUuid, String cartUuid, String cartName) {
        this.userUuid = userUuid;
        this.cartUuid = NanoIdGenerator.generateNanoId();
        this.cartName = cartName;
    }

    public Cart toEntity(){
        return Cart.builder()
                .userUuid(userUuid)
                .cartUuid(cartUuid)
                .cartName(cartName)
                .build();
    }
}

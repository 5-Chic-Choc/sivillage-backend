package com.chicchoc.sivillage.domain.cart.dto.in;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRequestDto {

    private String productOptionUuid;
    private Integer amount;

    public Cart toEntity(String cartUuid, String userUuid) {
        return Cart.builder()
                .cartUuid(cartUuid)
                .userUuid(userUuid)
                .productOptionUuid(productOptionUuid)
                .amount(amount)
                .isSelected(false)
                .build();
    }
}

package com.chicchoc.sivillage.domain.cart.dto.out;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.domain.cart.vo.out.CartResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {

    private String cartUuid;
    private String productUuid;
    private String productOptionUuid;
    private int quantity;
    private Boolean isSelected;

    public CartResponseVo toVo() {
        return CartResponseVo.builder()
                .cartUuid(cartUuid)
                .productUuid(productUuid)
                .productOptionUuid(productOptionUuid)
                .quantity(quantity)
                .isSelected(isSelected).build();
    }

    public static CartResponseDto fromEntity(Cart cart) {
        return CartResponseDto.builder()
                .cartUuid(cart.getCartUuid())
                .productUuid(cart.getProductUuid())
                .productOptionUuid(cart.getProductOptionUuid())
                .quantity(cart.getQuantity())
                .isSelected(cart.getIsSelected())
                .build();
    }
}

package com.chicchoc.sivillage.domain.cart.dto.out;

import com.chicchoc.sivillage.domain.cart.domain.CartProduct;
import com.chicchoc.sivillage.domain.cart.vo.out.CartProductResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductResponseDto {
    private String cartProductUuid;
    private String cartUuid;
    private String productOptionUuid;
    private int quantity;
    private Boolean isSelected;

    public CartProductResponseVo toVo(){
        return CartProductResponseVo.builder()
                .cartProductUuid(cartProductUuid)
                .cartUuid(cartUuid)
                .productOptionUuid(productOptionUuid)
                .quantity(quantity)
                .isSelected(isSelected)
                .build();
    }

    public static CartProductResponseDto fromEntity(CartProduct cartProduct){
        return CartProductResponseDto.builder()
                .cartProductUuid(cartProduct.getCartProductUuid())
                .cartUuid(cartProduct.getCartUuid())
                .productOptionUuid(cartProduct.getProductOptionUuid())
                .quantity(cartProduct.getQuantity())
                .isSelected(cartProduct.getIsSelected())
                .build();
    }
}

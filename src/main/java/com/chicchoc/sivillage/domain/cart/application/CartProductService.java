package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.dto.in.CartProductDeleteRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartProductRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartProductOptionUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartProductStatusUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartProductResponseDto;
import java.util.List;

public interface CartProductService {

    void addToCart(CartProductRequestDto cartProductRequestDto);

    List<CartProductResponseDto> getCartItem(String cartUuid);

    void deleteCartItems(List<CartProductDeleteRequestDto> cartProductDeleteRequestDtoList);

    CartProductResponseDto updateCartProductOption(CartProductOptionUpdateRequestDto cartProductOptionUpdateRequestDto);

    CartProductResponseDto updateCartProductStatus(CartProductStatusUpdateRequestDto cartProductStatusUpdateRequestDto);
}

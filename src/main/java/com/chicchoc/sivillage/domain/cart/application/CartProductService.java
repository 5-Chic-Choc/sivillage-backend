package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartProductResponseDto;

import java.util.List;

public interface CartProductService {
    void createCartProduct(CartRequestDto cartRequestDto);
    List<CartProductResponseDto> getCartProductList(String cartUuid);
}

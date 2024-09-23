package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.dto.in.CartDeleteRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartMigrateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.ItemQuantityUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import java.util.List;

public interface CartService {

    void createCart(CartRequestDto cartRequestDto);

    List<CartResponseDto> getCart(String userUuid);

    CartResponseDto updateCartItem(CartUpdateRequestDto cartUpdateRequestDto);

    void updateCartStatus(List<ItemQuantityUpdateRequestDto> cartUpdateAmountRequestDtoList);

    void deleteCartItems(List<CartDeleteRequestDto> cartDeleteRequestDtoList);

    void migrateCart(CartMigrateRequestDto cartMigrateRequestDto);
}

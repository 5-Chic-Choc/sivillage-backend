package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import java.util.List;

public interface CartService {

    void createCart(CartRequestDto cartRequestDto, String userUuid);

    List<CartResponseDto> getCart(String userUuid);

    void updateCart(CartUpdateRequestDto cartUpdateRequestDto, String cartUuid);
}

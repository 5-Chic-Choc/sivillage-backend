package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;

public interface CartService {

    void createCart(CartRequestDto cartRequestDto,String userUuid);
}

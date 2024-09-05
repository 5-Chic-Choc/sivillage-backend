package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import java.util.List;
import java.util.Optional;

public interface CartService {

    // 장바구니 생성
    void createCart(CartRequestDto cartRequestDto);

    // 장바구니 조회
    Optional<List<CartResponseDto>> getCart(String userUuid);

}

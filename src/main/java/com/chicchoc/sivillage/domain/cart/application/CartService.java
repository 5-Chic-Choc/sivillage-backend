package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartStatusUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import com.chicchoc.sivillage.domain.cart.vo.in.CartDeleteRequestVo;
import java.util.List;

public interface CartService {

    void createCart(CartRequestDto cartRequestDto, String userUuid);

    List<CartResponseDto> getCart(String userUuid);

    CartResponseDto updateCart(CartUpdateRequestDto cartUpdateRequestDto, String cartUuid);

    void updateCart(List<CartStatusUpdateRequestDto> cartUpdateAmountRequestDtoList);

    void deleteCartItems(List<CartDeleteRequestVo> cartDeleteRequestVoList);

    void migrateCart(String userUuid, String unsignedMemberUuid);
}

package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import com.chicchoc.sivillage.domain.cart.infrastructure.CartRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final NanoIdGenerator nanoIdGenerator;

    @Override
    public void createCart(CartRequestDto cartRequestDto) {
        // TODO 회원 비회원 확인 로직
        boolean isSigned = false;
        // TODO 회원, 비회원 userUuid 가져오는 로직
        String userUuid = null;
        // cartUuid 생성
        String CartUuid = nanoIdGenerator.generateNanoId();

        cartRepository.save(cartRequestDto.toEntity(CartUuid, isSigned, userUuid));
    }

    @Override
    public Optional<List<CartResponseDto>> getCart(String userUuid) {
        // TODO
        Optional<List<Cart>> carts = cartRepository.findByUserUuid(userUuid);
        if (carts.isEmpty()) {
            return ;
        }
        else {
            return
        }

    }
}

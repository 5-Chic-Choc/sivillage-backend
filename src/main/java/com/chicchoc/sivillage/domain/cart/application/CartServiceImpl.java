package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.domain.cart.infrastructure.CartRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final NanoIdGenerator nanoIdGenerator;

    @Override
    public void createCart() {
        // TODO 회원 비회원 확인 로직
        boolean isSigned = false;
        // TODO 회원, 비회원 userUuid 가져오는 로직
        String userUuid = "test1";
        // cartUuid 생성
        String cartUuid = nanoIdGenerator.generateNanoId();

        cartRepository.save(toEntity(cartUuid, isSigned, userUuid));
    }

    public Cart toEntity(String cartUuid, boolean isSigned, String userUuid) {
        return Cart.builder()
                .cartUuid(cartUuid)
                .isSigned(isSigned)
                .userUuid(userUuid)
                .build();
    }
}

package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.domain.cart.infrastructure.CartRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final NanoIdGenerator nanoIdGenerator;

    @Override
    public void createCart(String userUuid) {

        String cartUuid = nanoIdGenerator.generateNanoId();

        // cartRepository.save(toEntity(cartUuid, userUuid));
    }

    //public Cart toEntity(String cartUuid, String userUuid) {
    //    return Cart.builder()
    //            .cartUuid(cartUuid)
    //            .userUuid(userUuid)
    //            .build();
    //}
}

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

    @Override
    public void createCart(CartRequestDto cartRequestDto, String userUuid) {
        Optional<Cart> existCartItem = cartRepository.findByUserUuidAndProductOptionUuid(userUuid,
                cartRequestDto.getProductOptionUuid());

        if (existCartItem.isPresent()) {
            Cart cart = existCartItem.get();
            cart.setAmount(cart.getAmount() + cartRequestDto.getAmount());
            cartRepository.save(cart);
        } else {
            Cart newItem = cartRequestDto.toEntity(userUuid);
            cartRepository.save(newItem);
        }
    }

    @Override
    public List<CartResponseDto> getCart(String userUuid) {
        List<Cart> cartList = cartRepository.findByUserUuid(userUuid);

        return cartList.stream()
                .map(cart -> CartResponseDto.builder()
                        .productOptionUuid(cart.getProductOptionUuid())
                        .amount(cart.getAmount())
                        .isSelected(cart.getIsSelected())
                        .build()
                ).toList();
    }
}

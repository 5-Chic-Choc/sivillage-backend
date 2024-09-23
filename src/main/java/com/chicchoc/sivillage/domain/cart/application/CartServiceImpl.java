package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.domain.cart.dto.in.CartDeleteRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartMigrateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.ItemQuantityUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import com.chicchoc.sivillage.domain.cart.infrastructure.CartRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public void createCart(CartRequestDto cartRequestDto) {

        Optional<Cart> existingCartItem = cartRepository.findByUserUuidAndProductOptionUuid(
                cartRequestDto.getUserUuid(), cartRequestDto.getProductOptionUuid());

        cartRepository.save(existingCartItem.map(cart -> Cart.builder()
                        .id(cart.getId())
                        .userUuid(cart.getUserUuid())
                        .cartUuid(cart.getCartUuid())
                        .productUuid(cart.getProductUuid())
                        .productOptionUuid(cart.getProductOptionUuid())
                        .quantity(cart.getQuantity() + cartRequestDto.getQuantity()) // 수량 업데이트
                        .isSelected(cart.getIsSelected())
                        .build())
                .orElseGet(cartRequestDto::toEntity));
    }

    @Override
    public List<CartResponseDto> getCart(String userUuid) {
        return cartRepository.findByUserUuid(userUuid).stream()
                .map(cart -> CartResponseDto.builder()
                        .cartUuid(cart.getCartUuid())
                        .productUuid(cart.getProductUuid())
                        .productOptionUuid(cart.getProductOptionUuid())
                        .quantity(cart.getQuantity())
                        .isSelected(cart.getIsSelected())
                        .build()
                ).toList();
    }

    @Override
    public CartResponseDto updateCartItem(CartUpdateRequestDto cartUpdateRequestDto) {

        Cart existCartItem = cartRepository.findByCartUuid(cartUpdateRequestDto.getCartUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART));

        return CartResponseDto.fromEntity(cartRepository.save(Cart.builder()
                .id(existCartItem.getId())
                .userUuid(existCartItem.getUserUuid())
                .cartUuid(existCartItem.getCartUuid())
                .productUuid(existCartItem.getProductUuid())
                .productOptionUuid(cartUpdateRequestDto.getProductOptionUuid())
                .quantity(cartUpdateRequestDto.getQuantity())
                .isSelected(existCartItem.getIsSelected())
                .build()));
    }

    @Override
    public void updateCartStatus(List<ItemQuantityUpdateRequestDto> itemQuantityUpdateRequestDtoList) {
        itemQuantityUpdateRequestDtoList.stream()
                .map(itemQuantityUpdateRequestDto -> cartRepository.findByCartUuid(
                                itemQuantityUpdateRequestDto.getCartUuid())
                        .map(cart -> Cart.builder()
                                .id(cart.getId())
                                .userUuid(cart.getUserUuid())
                                .cartUuid(cart.getCartUuid())
                                .productOptionUuid(cart.getProductOptionUuid())
                                .quantity(itemQuantityUpdateRequestDto.getQuantity())
                                .build())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART))
                )
                .forEach(cartRepository::save);
    }

    @Override
    public void deleteCartItems(List<CartDeleteRequestDto> cartDeleteRequestDtoList) {
        cartDeleteRequestDtoList.stream()
                .map(cartDeleteRequestDto -> cartRepository.findByCartUuid(cartDeleteRequestDto.getCartUuid())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART))
                ).forEach(cartRepository::delete);
    }

    @Override
    public void migrateCart(CartMigrateRequestDto cartMigrateRequestDto) {

        List<Cart> unsignedMemberCartList = cartRepository.findByUserUuid(cartMigrateRequestDto.getUnsignedUserUuid());
        List<Cart> signedMemberCartList = cartRepository.findByUserUuid(cartMigrateRequestDto.getUserUuid());

        List<Cart> updatedCartList = new ArrayList<>();

        if (!unsignedMemberCartList.isEmpty()) {
            unsignedMemberCartList.forEach(cart ->
                    signedMemberCartList.stream()
                            .filter(userCart -> userCart.getProductOptionUuid().equals(cart.getProductOptionUuid()))
                            .findFirst()
                            .ifPresentOrElse(existCart -> {
                                cartRepository.save(Cart.builder()
                                        .id(existCart.getId())
                                        .cartUuid(existCart.getCartUuid())
                                        .userUuid(existCart.getUserUuid())
                                        .productOptionUuid(existCart.getProductOptionUuid())
                                        .quantity(existCart.getQuantity() + cart.getQuantity())
                                        .isSelected(cart.getIsSelected())
                                        .build());
                                cartRepository.delete(cart);
                            }, () -> {
                                updatedCartList.add(Cart.builder()
                                        .id(cart.getId())
                                        .cartUuid(cart.getCartUuid())
                                        .userUuid(cartMigrateRequestDto.getUserUuid())
                                        .productOptionUuid(cart.getProductOptionUuid())
                                        .quantity(cart.getQuantity())
                                        .isSelected(cart.getIsSelected())
                                        .build());
                            })
            );
            cartRepository.saveAll(updatedCartList);
        }
    }
}
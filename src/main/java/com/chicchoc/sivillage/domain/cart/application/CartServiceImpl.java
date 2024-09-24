package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.domain.cart.dto.in.CartDeleteRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartMigrateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.ItemIsSelectedUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.ItemQuantityUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import com.chicchoc.sivillage.domain.cart.infrastructure.CartRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
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

        Optional<Cart> existingCartItem = cartRepository.findByUserUuidAndProductOptionUuid(
                cartUpdateRequestDto.getUserUuid(), cartUpdateRequestDto.getProductOptionUuid());

        Cart cartItem = cartRepository.findByCartUuid(cartUpdateRequestDto.getCartUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART));

        Cart cart;
        if (existingCartItem.isPresent()) {

            Cart item = existingCartItem.get();

            cartRepository.delete(cartItem);

            cart = Cart.builder()
                    .id(item.getId())
                    .userUuid(item.getUserUuid())
                    .cartUuid(item.getCartUuid())
                    .productUuid(item.getProductUuid())
                    .productOptionUuid(item.getProductOptionUuid())
                    .quantity(item.getQuantity() + cartUpdateRequestDto.getQuantity())
                    .isSelected(item.getIsSelected())
                    .build();
        } else {

            cart = Cart.builder()
                    .id(cartItem.getId())
                    .userUuid(cartItem.getUserUuid())
                    .cartUuid(cartItem.getCartUuid())
                    .productUuid(cartItem.getProductUuid())
                    .productOptionUuid(cartUpdateRequestDto.getProductOptionUuid())
                    .quantity(cartUpdateRequestDto.getQuantity())
                    .isSelected(cartItem.getIsSelected())
                    .build();
        }

        return CartResponseDto.fromEntity(cartRepository.save(cart));
    }

    @Override
    public void updateItemQuantity(ItemQuantityUpdateRequestDto itemQuantityUpdateRequestDto) {
        Cart cart = cartRepository.findByCartUuid(itemQuantityUpdateRequestDto.getCartUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART));

        cartRepository.save(Cart.builder()
                .id(cart.getId())
                .userUuid(cart.getUserUuid())
                .cartUuid(cart.getCartUuid())
                .productUuid(cart.getProductUuid())
                .quantity(itemQuantityUpdateRequestDto.getQuantity())
                .productOptionUuid(cart.getProductOptionUuid())
                .isSelected(cart.getIsSelected())
                .build());
    }

    @Override
    public void updateItemIsSelected(List<ItemIsSelectedUpdateRequestDto> ItemIsSelectedUpdateRequestDtoList) {
        ItemIsSelectedUpdateRequestDtoList.stream().map(itemIsSelectedUpdateRequestDto -> {

            Cart cart = cartRepository.findByCartUuid(itemIsSelectedUpdateRequestDto.getCartUuid())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART));

            return Cart.builder()
                    .id(cart.getId())
                    .cartUuid(cart.getCartUuid())
                    .userUuid(cart.getUserUuid())
                    .productUuid(cart.getProductUuid())
                    .productOptionUuid(cart.getProductOptionUuid())
                    .quantity(cart.getQuantity())
                    .isSelected(itemIsSelectedUpdateRequestDto.getIsSelected())
                    .build();

        }).forEach(cartRepository::save);
        // 리턴 값 필요하면 리턴ㄴ하기
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

        Map<String, Cart> signedCartMap = signedMemberCartList.stream()
                .collect(Collectors.toMap(Cart::getProductOptionUuid, Function.identity()));

        List<Cart> updatedCartList = unsignedMemberCartList.stream()
                .map(unsignedCart -> {
                    Cart existCart = signedCartMap.get(unsignedCart.getProductOptionUuid());

                    if (existCart != null) {

                        cartRepository.delete(unsignedCart);
                        return Cart.builder()
                                .id(existCart.getId())
                                .cartUuid(existCart.getCartUuid())
                                .userUuid(existCart.getUserUuid())
                                .productUuid(existCart.getProductUuid())
                                .productOptionUuid(existCart.getProductOptionUuid())
                                .quantity(existCart.getQuantity() + unsignedCart.getQuantity())
                                .isSelected(existCart.getIsSelected())
                                .build();
                    } else {
                        return Cart.builder()
                                .id(unsignedCart.getId())
                                .cartUuid(unsignedCart.getCartUuid())
                                .userUuid(cartMigrateRequestDto.getUserUuid())
                                .productUuid(unsignedCart.getProductUuid())
                                .productOptionUuid(unsignedCart.getProductOptionUuid())
                                .quantity(unsignedCart.getQuantity())
                                .isSelected(unsignedCart.getIsSelected())
                                .build();
                    }
                })
                .collect(Collectors.toList());

        if (!updatedCartList.isEmpty()) {
            cartRepository.saveAll(updatedCartList);
        }

    }
}
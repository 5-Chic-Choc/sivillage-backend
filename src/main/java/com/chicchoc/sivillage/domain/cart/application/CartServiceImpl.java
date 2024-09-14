package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartStatusUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import com.chicchoc.sivillage.domain.cart.infrastructure.CartRepository;
import com.chicchoc.sivillage.domain.cart.vo.in.CartDeleteRequestVo;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import java.util.ArrayList;
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
    public void createCart(CartRequestDto cartRequestDto, String userUuid) {
        Optional<Cart> existCartItem = cartRepository.findByUserUuidAndProductOptionUuid(userUuid,
                cartRequestDto.getProductOptionUuid());

        String cartUuid = nanoIdGenerator.generateNanoId();

        if (existCartItem.isPresent()) {
            Cart cart = existCartItem.get();
            cartRepository.save(Cart.builder()
                    .id(cart.getId())
                    .userUuid(userUuid)
                    .cartUuid(cart.getCartUuid())
                    .productOptionUuid(cart.getProductOptionUuid())
                    .amount(cart.getAmount() + cartRequestDto.getAmount())
                    .isSelected(cart.getIsSelected())
                    .build());
        } else {
            Cart newItem = cartRequestDto.toEntity(cartUuid, userUuid);
            cartRepository.save(newItem);
        }
    }

    @Override
    public List<CartResponseDto> getCart(String userUuid) {
        List<Cart> cartList = cartRepository.findByUserUuid(userUuid);

        return cartList.stream()
                .map(cart -> CartResponseDto.builder()
                        .cartUuid(cart.getCartUuid())
                        .productOptionUuid(cart.getProductOptionUuid())
                        .amount(cart.getAmount())
                        .isSelected(cart.getIsSelected())
                        .build()
                ).toList();
    }

    @Override
    public CartResponseDto updateCart(CartUpdateRequestDto cartUpdateRequestDto, String cartUuid) {
        Optional<Cart> existCartItem = cartRepository.findByCartUuid(cartUuid);

        if (existCartItem.isPresent()) {
            Cart cart = existCartItem.get();

            Cart updateCart = Cart.builder()
                    .id(cart.getId())
                    .userUuid(cart.getUserUuid())
                    .cartUuid(cart.getCartUuid())
                    .productOptionUuid(cartUpdateRequestDto.getProductOptionUuid())
                    .amount(cartUpdateRequestDto.getAmount())
                    .isSelected(cart.getIsSelected())
                    .build();

            cartRepository.save(updateCart);

            return CartResponseDto.builder()
                    .cartUuid(cart.getCartUuid())
                    .productOptionUuid(cart.getProductOptionUuid())
                    .amount(cart.getAmount())
                    .isSelected(cart.getIsSelected())
                    .build();
        }
        // TODO 예외처리 해야함
        return null;
    }

    @Override
    public void updateCart(List<CartStatusUpdateRequestDto> cartStatusUpdateRequestDtoList) {

        for (CartStatusUpdateRequestDto cartStatusUpdateRequestDto : cartStatusUpdateRequestDtoList) {
            Optional<Cart> existCartItem = cartRepository.findByCartUuid(cartStatusUpdateRequestDto.getCartUuid());

            if (existCartItem.isPresent()) {
                Cart cart = existCartItem.get();

                Integer updatedAmount =
                        cartStatusUpdateRequestDto.getAmount() != null ? cartStatusUpdateRequestDto.getAmount()
                                : cart.getAmount();
                Boolean updatedIsSelected =
                        cartStatusUpdateRequestDto.getIsSelected() != null ? cartStatusUpdateRequestDto.getIsSelected()
                                : cart.getIsSelected();

                Cart updatedCart = Cart.builder()
                        .id(cart.getId())
                        .userUuid(cart.getUserUuid())
                        .cartUuid(cart.getCartUuid())
                        .productOptionUuid(cart.getProductOptionUuid())
                        .amount(updatedAmount)
                        .isSelected(updatedIsSelected)
                        .build();

                cartRepository.save(updatedCart);
            } else {
                // TODO 예외처리 해야함
            }
        }
    }

    @Override
    public void deleteCartItems(List<CartDeleteRequestVo> cartDeleteRequestVoList) {

        for (CartDeleteRequestVo cartDeleteRequestVo : cartDeleteRequestVoList) {
            Optional<Cart> existCartItem = cartRepository.findByCartUuid(cartDeleteRequestVo.getCartUuid());

            if (existCartItem.isPresent()) {
                cartRepository.delete(existCartItem.get());
            } else {
                // TODO 예외처리
            }
        }
    }

    @Override
    public void migrateCart(String SignedUserUuid, String unsignedMemberUuid) {

        List<Cart> UnsignedMemberCartList = cartRepository.findByUserUuid(unsignedMemberUuid);
        List<Cart> SignedMemberCartList = cartRepository.findByUserUuid(SignedUserUuid);

        List<Cart> updatedCartList = new ArrayList<>();

        if (!UnsignedMemberCartList.isEmpty()) {
            for (Cart cart : UnsignedMemberCartList) {
                // 회원 장바구니에서 상품 찾는 로직
                Optional<Cart> existCartItem = SignedMemberCartList.stream()
                        .filter(userCart -> userCart.getProductOptionUuid()
                                .equals(cart.getProductOptionUuid()))
                        .findFirst();

                if (existCartItem.isPresent()) {
                    Cart existCart = existCartItem.get();

                    Cart updatedCart = Cart.builder()
                            .id(existCart.getId())
                            .cartUuid(existCart.getCartUuid())
                            .userUuid(existCart.getUserUuid())
                            .productOptionUuid(existCart.getProductOptionUuid())
                            .amount(existCart.getAmount() + cart.getAmount())
                            .isSelected(cart.getIsSelected())
                            .build();

                    cartRepository.save(updatedCart);
                    cartRepository.delete(cart);

                } else {
                    Cart updateCart = Cart.builder()
                            .id(cart.getId())
                            .cartUuid(cart.getCartUuid())
                            .userUuid(SignedUserUuid)
                            .productOptionUuid(cart.getProductOptionUuid())
                            .amount(cart.getAmount())
                            .isSelected(cart.getIsSelected())
                            .build();
                    updatedCartList.add(updateCart);
                }
            }
            cartRepository.saveAll(updatedCartList);
        }
    }


}

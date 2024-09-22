package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import com.chicchoc.sivillage.domain.cart.infrastructure.CartRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public void createCart(CartRequestDto cartRequestDto) {

        cartRepository.findByUserUuidAndCartName(cartRequestDto.getUserUuid(), cartRequestDto.getCartName())
                .ifPresent(cart -> {
                    throw new BaseException(BaseResponseStatus.EXIST_CART_NAME);
                });

        cartRepository.save(cartRequestDto.toEntity());
    }

    @Override
    public List<CartResponseDto> getCart(String userUuid) {
        return cartRepository.findByUserUuid(userUuid).stream().map(CartResponseDto::fromDto).toList();
    }

    @Override
    public CartResponseDto updateCartName(CartUpdateRequestDto cartUpdateRequestDto) {
        Cart cart = cartRepository.findByCartUuid(cartUpdateRequestDto.getCartUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART));

        return CartResponseDto.fromDto(cartRepository.save(Cart.builder()
                .id(cart.getId())
                .cartUuid(cart.getCartUuid())
                .cartName(cartUpdateRequestDto.getCartName())
                .userUuid(cart.getUserUuid())
                .build()));
    }

    @Override
    public void deleteCart(String cartUuid) {
        cartRepository.delete(cartRepository.findByCartUuid(cartUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART)));
    }

//    @Override
//    public void createCart(CartRequestDto cartRequestDto) {
//
//        Optional<Cart> existCartItem = cartRepository.findByUserUuidAndProductOptionUuid(cartRequestDto.getUserUuid(),
//                cartRequestDto.getProductOptionUuid());
//
//        if (existCartItem.isPresent()) {
//            cartRepository.save(Cart.builder()
//                    .id(existCartItem.get().getId())
//                    .userUuid(existCartItem.get().getUserUuid())
//                    .cartUuid(existCartItem.get().getCartUuid())
//                    .productOptionUuid(existCartItem.get().getProductOptionUuid())
//                    .amount(existCartItem.get().getAmount() + cartRequestDto.getAmount())
//                    .isSelected(existCartItem.get().getIsSelected())
//                    .build());
//        } else {
//            cartRepository.save(cartRequestDto.toEntity());
//        }
//    }

//    @Override
//    public List<CartResponseDto> getCart(String userUuid) {
//        return cartRepository.findByUserUuid(userUuid).stream()
//                .map(cart -> CartResponseDto.builder()
//                        .cartUuid(cart.getCartUuid())
//                        .productOptionUuid(cart.getProductOptionUuid())
//                        .amount(cart.getAmount())
//                        .isSelected(cart.getIsSelected())
//                        .build()
//                ).toList();
//    }

//    @Override
//    public CartResponseDto updateCartItem(CartUpdateRequestDto cartUpdateRequestDto) {
//
//        Cart existCartItem = cartRepository.findByCartUuid(cartUpdateRequestDto.getCartUuid())
//                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART));
//
//        Cart savedData = cartRepository.save(Cart.builder()
//                .id(existCartItem.getId())
//                .userUuid(existCartItem.getUserUuid())
//                .cartUuid(existCartItem.getCartUuid())
//                .productOptionUuid(cartUpdateRequestDto.getProductOptionUuid())
//                .amount(cartUpdateRequestDto.getAmount())
//                .isSelected(existCartItem.getIsSelected())
//                .build());
//
//        return CartResponseDto.builder()
//                .cartUuid(savedData.getCartUuid())
//                .productOptionUuid(savedData.getProductOptionUuid())
//                .amount(savedData.getAmount())
//                .isSelected(savedData.getIsSelected())
//                .build();
//    }
//
//    @Override
//    public void updateCartStatus(List<CartStatusUpdateRequestDto> cartStatusUpdateRequestDtoList) {
//        cartStatusUpdateRequestDtoList.stream()
//                .map(cartStatusUpdateRequestDto -> cartRepository.findByCartUuid(
//                                cartStatusUpdateRequestDto.getCartUuid())
//                        .map(cart -> Cart.builder()
//                                .id(cart.getId())
//                                .userUuid(cart.getUserUuid())
//                                .cartUuid(cart.getCartUuid())
//                                .productOptionUuid(cart.getProductOptionUuid())
//                                .amount(cartStatusUpdateRequestDto.getAmount())
//                                .isSelected(cartStatusUpdateRequestDto.getIsSelected())
//                                .build())
//                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART))
//                )
//                .forEach(cartRepository::save);
//    }
//
//    @Override
//    public void deleteCartItems(List<CartDeleteRequestDto> cartDeleteRequestDtoList) {
//        cartDeleteRequestDtoList.stream()
//                .map(cartDeleteRequestDto -> cartRepository.findByCartUuid(cartDeleteRequestDto.getCartUuid())
//                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART))
//                ).forEach(cartRepository::delete);
//    }
//
//    @Override
//    public void migrateCart(CartMigrateRequestDto cartMigrateRequestDto) {
//
//        List<Cart> unsignedMemberCartList = cartRepository.findByUserUuid(cartMigrateRequestDto.getUnsignedUserUuid());
//        List<Cart> signedMemberCartList = cartRepository.findByUserUuid(cartMigrateRequestDto.getUserUuid());
//
//        List<Cart> updatedCartList = new ArrayList<>();
//
//        if (!unsignedMemberCartList.isEmpty()) {
//            unsignedMemberCartList.forEach(cart ->
//                    signedMemberCartList.stream()
//                            .filter(userCart -> userCart.getProductOptionUuid().equals(cart.getProductOptionUuid()))
//                            .findFirst()
//                            .ifPresentOrElse(existCart -> {
//                                cartRepository.save(Cart.builder()
//                                        .id(existCart.getId())
//                                        .cartUuid(existCart.getCartUuid())
//                                        .userUuid(existCart.getUserUuid())
//                                        .productOptionUuid(existCart.getProductOptionUuid())
//                                        .amount(existCart.getAmount() + cart.getAmount())
//                                        .isSelected(cart.getIsSelected())
//                                        .build());
//                                cartRepository.delete(cart);
//                            }, () -> {
//                                updatedCartList.add(Cart.builder()
//                                        .id(cart.getId())
//                                        .cartUuid(cart.getCartUuid())
//                                        .userUuid(cartMigrateRequestDto.getUserUuid())
//                                        .productOptionUuid(cart.getProductOptionUuid())
//                                        .amount(cart.getAmount())
//                                        .isSelected(cart.getIsSelected())
//                                        .build());
//                            })
//            );
//            cartRepository.saveAll(updatedCartList);
//        }
//    }
}
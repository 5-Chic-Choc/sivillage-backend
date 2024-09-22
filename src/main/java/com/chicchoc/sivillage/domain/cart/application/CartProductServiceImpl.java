package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.domain.CartProduct;
import com.chicchoc.sivillage.domain.cart.dto.in.CartProductDeleteRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartProductOptionUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartProductRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.in.CartProductStatusUpdateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartProductResponseDto;
import com.chicchoc.sivillage.domain.cart.infrastructure.CartProductRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartProductServiceImpl implements CartProductService {

    private final CartProductRepository cartProductRepository;

    @Override
    public void addToCart(CartProductRequestDto cartProductRequestDto) {

        cartProductRepository.save(cartProductRepository.findByCartUuidAndProductOptionUuid(
                        cartProductRequestDto.getCartUuid(), cartProductRequestDto.getProductOptionUuid())
                .map(item -> updateCartProductQuantity(item, cartProductRequestDto.getQuantity()))
                .orElseGet(cartProductRequestDto::toEntity));
    }

    @Override
    public List<CartProductResponseDto> getCartItem(String cartUuid) {
        return cartProductRepository.findByCartUuid(cartUuid).stream().map(CartProductResponseDto::fromEntity).toList();
    }

    @Override
    public void deleteCartItems(List<CartProductDeleteRequestDto> cartProductDeleteRequestDtoList) {
        cartProductDeleteRequestDtoList.stream()
                .map(CartProductDeleteRequestDto::getCartUuid)
                .forEach(cartProductRepository::deleteByCartUuid);
    }

    @Override
    public CartProductResponseDto updateCartProductOption(
            CartProductOptionUpdateRequestDto cartProductOptionUpdateRequestDto) {

        CartProduct product = cartProductRepository.findByCartProductUuid(
                        cartProductOptionUpdateRequestDto.getCartProductUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART));

        return CartProductResponseDto.fromEntity(cartProductRepository.save(CartProduct.builder()
                .cartProductUuid(product.getCartProductUuid())
                .cartUuid(product.getProductOptionUuid())
                .productOptionUuid(cartProductOptionUpdateRequestDto.getProductOptionUuid())
                .quantity(cartProductOptionUpdateRequestDto.getQuantity())
                .isSelected(product.getIsSelected())
                .build()));
    }

    @Override
    public CartProductResponseDto updateCartProductStatus(
            CartProductStatusUpdateRequestDto cartProductStatusUpdateRequestDto) {

        CartProduct product = cartProductRepository.findByCartProductUuid(
                        cartProductStatusUpdateRequestDto.getCartProductUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART));

        return CartProductResponseDto.fromEntity(cartProductRepository.save(CartProduct.builder()
                .cartProductUuid(product.getCartProductUuid())
                .cartUuid(product.getProductOptionUuid())
                .productOptionUuid(product.getProductOptionUuid())
                .quantity(cartProductStatusUpdateRequestDto.getQuantity())
                .isSelected(cartProductStatusUpdateRequestDto.getIsSelected())
                .build()));
    }


    private CartProduct updateCartProductQuantity(CartProduct existingCartProduct, int additionalQuantity) {
        return CartProduct.builder()
                .id(existingCartProduct.getId())
                .cartProductUuid(existingCartProduct.getCartProductUuid())
                .cartUuid(existingCartProduct.getCartUuid())
                .productOptionUuid(existingCartProduct.getProductOptionUuid())
                .quantity(existingCartProduct.getQuantity() + additionalQuantity)
                .isSelected(existingCartProduct.getIsSelected())
                .build();
    }
}

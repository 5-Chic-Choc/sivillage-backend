package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.domain.CartProduct;
import com.chicchoc.sivillage.domain.cart.dto.in.CartProductRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartProductResponseDto;
import com.chicchoc.sivillage.domain.cart.infrastructure.CartProductRepository;
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

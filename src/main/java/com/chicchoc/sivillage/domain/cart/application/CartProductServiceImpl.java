package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.domain.CartProduct;
import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartProductResponseDto;
import com.chicchoc.sivillage.domain.cart.infrastructure.CartProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartProductServiceImpl implements CartProductService {

    private final CartProductRepository cartProductRepository;

    @Override
    public void createCartProduct(CartRequestDto cartRequestDto) {

    }

    @Override
    public List<CartProductResponseDto> getCartProductList(String cartUuid) {
        List<CartProduct> cartProductList = cartProductRepository.findByCartUuid(cartUuid);

        return cartProductList.stream()
                .map(cartProduct -> CartProductResponseDto.builder()
                        .cartProductUuid(cartProduct.getCartProductUuid())
                        .productOrderOptionId(cartProduct.getProductOrderOptionId())
                        .amount(cartProduct.getAmount())
                        .build()
                )
                .toList();
    }
}

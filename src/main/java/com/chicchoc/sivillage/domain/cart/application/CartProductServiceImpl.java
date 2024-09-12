package com.chicchoc.sivillage.domain.cart.application;

import com.chicchoc.sivillage.domain.cart.domain.CartProduct;
import com.chicchoc.sivillage.domain.cart.dto.in.CartProductRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartProductResponseDto;
import com.chicchoc.sivillage.domain.cart.infrastructure.CartProductRepository;
import lombok.RequiredArgsConstructor;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartProductServiceImpl implements CartProductService {

    private final CartProductRepository cartProductRepository;
    private final NanoIdGenerator nanoIdGenerator;

    @Override
    public void createCartProduct(CartProductRequestDto cartProductRequestDto) {
        String cartProductUuid = nanoIdGenerator.generateNanoId();

        cartProductRepository.save(cartProductRequestDto.toEntity(cartProductUuid));
    }

    @Override
    public List<CartProductResponseDto> getCartProductList(String cartUuid) {
        List<CartProduct> cartProductList = cartProductRepository.findByCartUuid(cartUuid);

        return cartProductList.stream()
                .map(cartProduct -> CartProductResponseDto.builder().cartProductUuid(cartProduct.getCartProductUuid())
                        .productOptionUuid(cartProduct.getProductOptionUuid()).amount(cartProduct.getAmount())
                        .build()).toList();
    }
}

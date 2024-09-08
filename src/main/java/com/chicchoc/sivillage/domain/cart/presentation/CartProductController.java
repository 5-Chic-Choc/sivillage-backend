package com.chicchoc.sivillage.domain.cart.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartProductService;
import com.chicchoc.sivillage.domain.cart.dto.in.CartProductRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartProductResponseDto;
import com.chicchoc.sivillage.domain.cart.vo.in.CartProductRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.out.CartProductResponseVo;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cartProduct")
public class CartProductController {

    private final CartProductService cartProductService;

    @PostMapping
    public CommonResponseEntity<Void> createCartProduct(@RequestBody CartProductRequestVo cartProductRequestVo) {
        CartProductRequestDto cartProductRequestDto = CartProductRequestDto.builder()
                .cartUuid(cartProductRequestVo.getCartUuid())
                .productOrderOptionId(cartProductRequestVo.getProductOrderOptionId())
                .amount(cartProductRequestVo.getAmount())
                .build();

        cartProductService.createCartProduct(cartProductRequestDto);

        return new CommonResponseEntity<>(
                HttpStatus.CREATED,
                "장바구니 제품 생성 성공",
                null
        );
    }

    @GetMapping("/list/{cartUuid}")
    public CommonResponseEntity<List<CartProductResponseVo>> getProductList(@PathVariable("cartUuid") String cartUuid) {
        List<CartProductResponseDto> cartProductResponseDto = cartProductService.getCartProductList(cartUuid);
        List<CartProductResponseVo> cartProductResponseVo = cartProductResponseDto.stream()
                .map(CartProductResponseDto::toCartProductResponseVo)
                .toList();
        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "장바구니 제품 불러오기 성공",
                cartProductResponseVo
        );
    }
}

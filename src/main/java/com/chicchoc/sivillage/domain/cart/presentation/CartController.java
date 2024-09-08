package com.chicchoc.sivillage.domain.cart.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartProductService;
import com.chicchoc.sivillage.domain.cart.application.CartService;
import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartProductResponseDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import com.chicchoc.sivillage.domain.cart.vo.in.CartRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.out.CartProductResponseVo;
import com.chicchoc.sivillage.domain.cart.vo.out.CartResponseVo;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public CommonResponseEntity<Void> createCart(@RequestBody CartRequestVo cartRequestVo) {
        CartRequestDto cartRequestDto = CartRequestDto.builder()
                .cartName(cartRequestVo.getCartName())
                .build();

        cartService.createCart(cartRequestDto);

        return new CommonResponseEntity<>(
                HttpStatus.CREATED,
                "장바구니 생성 성공",
                null
        );
    }

    @GetMapping("/list")
    public CommonResponseEntity<List<CartResponseVo>> getCartList() {
        // jwt로 userUuid 가져오기
        String userUuid = "test1";
        List<CartResponseDto> cartResponseDto = cartService.getCartUuidList(userUuid);
        List<CartResponseVo> cartResponseVoList = cartResponseDto.stream()
                .map(CartResponseDto::toCartResponseVo)
                .toList();

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "장바구니 불러오기 성공",
                cartResponseVoList
        );
    }
}

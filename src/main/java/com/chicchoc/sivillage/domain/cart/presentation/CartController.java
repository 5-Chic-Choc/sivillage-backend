package com.chicchoc.sivillage.domain.cart.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartProductService;
import com.chicchoc.sivillage.domain.cart.application.CartService;
import com.chicchoc.sivillage.domain.cart.dto.out.CartProductResponseDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import com.chicchoc.sivillage.domain.cart.vo.out.CartProductResponseVo;
import com.chicchoc.sivillage.domain.cart.vo.out.CartResponseVo;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final CartProductService cartProductService;

    @GetMapping("/list")
    public CommonResponseEntity<List<CartResponseVo>> getCartList() {
        // jwt로 userUuid 가져오기
        String userUuid = "1";
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

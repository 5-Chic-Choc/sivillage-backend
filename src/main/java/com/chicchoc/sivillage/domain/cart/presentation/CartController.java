package com.chicchoc.sivillage.domain.cart.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartService;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public CommonResponseEntity<Void> createCart() {
        cartService.createCart();

        return new CommonResponseEntity<>(
                HttpStatus.CREATED,
                "장바구니 생성 성공",
                null
        );
    }

}

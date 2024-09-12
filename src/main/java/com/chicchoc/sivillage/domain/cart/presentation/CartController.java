package com.chicchoc.sivillage.domain.cart.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartService;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping
    public BaseResponse<Void> createCart(Authentication authentication,
            @RequestHeader(value = "X-Unsigned-User-UUID", required = false) String unsignedUserUuid) {
        if (authentication == null) {
            cartService.createCart(unsignedUserUuid);
        } else {
            cartService.createCart(authentication.getName());
        }
        return new BaseResponse<>();
    }
}

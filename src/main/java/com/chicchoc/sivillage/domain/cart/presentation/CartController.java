package com.chicchoc.sivillage.domain.cart.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartService;
import com.chicchoc.sivillage.domain.cart.dto.in.CartRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import com.chicchoc.sivillage.domain.cart.vo.in.CartRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.out.CartResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            @RequestHeader(value = "X-Unsigned-User-UUID", required = false) String unsignedUserUuid,
            @RequestBody CartRequestVo cartRequestVo
    ) {
        CartRequestDto cartRequestDto = cartRequestVo.toDto();

        String userIdentifier = getUserIdentifier(authentication, unsignedUserUuid);

        cartService.createCart(cartRequestDto, userIdentifier);

        return new BaseResponse<>();
    }

    @GetMapping
    public BaseResponse<List<CartResponseVo>> getCart(Authentication authentication,
            @RequestHeader(value = "X-Unsigned-User-UUID", required = false) String unsignedUserUuid) {
        String userIdentifier = getUserIdentifier(authentication, unsignedUserUuid);

        List<CartResponseVo> cartResponseVoList = cartService.getCart(userIdentifier).stream()
                .map(CartResponseDto::toVo)
                .toList();

        return new BaseResponse<>(cartResponseVoList);
    }

    private String getUserIdentifier(Authentication authentication, String unsignedUserUuid) {
        return (authentication != null) ? authentication.getName() : unsignedUserUuid;
    } // 재사용성이 떨어지므로 Jwtutil에 넣어도 좋음

}

package com.chicchoc.sivillage.domain.cart.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartService;
import com.chicchoc.sivillage.domain.cart.dto.in.CartMigrateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import com.chicchoc.sivillage.domain.cart.vo.in.CartDeleteRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.in.CartRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.in.ItemIsSelectedUpdateRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.in.ItemQuantityUpdateRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.in.CartUpdateRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.out.CartResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.jwt.util.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public BaseResponse<Void> createCart(@AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Unsigned-User-UUID", required = false) String unsignedUserUuid,
            @RequestBody CartRequestVo cartRequestVo
    ) {

        cartService.createCart(cartRequestVo.toDto(JwtUtil.getUserIdentifier(userDetails, unsignedUserUuid)));

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @GetMapping
    public BaseResponse<List<CartResponseVo>> getCart(@AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Unsigned-User-UUID", required = false) String unsignedUserUuid) {

        return new BaseResponse<>(
                cartService.getCart(JwtUtil.getUserIdentifier(userDetails, unsignedUserUuid)).stream()
                        .map(CartResponseDto::toVo).toList()
        );
    }

    @PutMapping("/option/{cartUuid}")
    public BaseResponse<CartResponseVo> updateCartItem(@AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Unsigned-User-UUID", required = false) String unsignedUserUuid,
            @PathVariable String cartUuid,
            @RequestBody CartUpdateRequestVo cartUpdateRequestVo) {

        return new BaseResponse<>(cartService.updateCartItem(
                cartUpdateRequestVo.toDto(JwtUtil.getUserIdentifier(userDetails, unsignedUserUuid), cartUuid)).toVo());
    }

    @PutMapping("/quantity")
    public BaseResponse<Void> updateItemQuantity(
            @RequestBody ItemQuantityUpdateRequestVo itemQuantityUpdateRequestVo) {

        cartService.updateItemQuantity(itemQuantityUpdateRequestVo.toDto());

        return new BaseResponse<>();
    }

    @PutMapping("/isSelected")
    public BaseResponse<Void> updateItemIsSelected(
            @RequestBody List<ItemIsSelectedUpdateRequestVo> ItemIsSelectedUpdateRequestVoList) {

        cartService.updateItemIsSelected(ItemIsSelectedUpdateRequestVoList.stream().map(
                ItemIsSelectedUpdateRequestVo::toDto
        ).toList());

        return new BaseResponse<>();
    }

    @DeleteMapping
    public BaseResponse<Void> deleteCartItems(@RequestBody List<CartDeleteRequestVo> cartDeleteRequestVoList) {
        cartService.deleteCartItems(cartDeleteRequestVoList.stream()
                .map(CartDeleteRequestVo::toDto)
                .toList());
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @PostMapping("/migrate")
    public BaseResponse<Void> migrateCart(@AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Unsigned-User-UUID", required = false) String unsignedUserUuid
    ) {
        cartService.migrateCart(CartMigrateRequestDto.builder()
                .userUuid(userDetails.getUsername())
                .unsignedUserUuid(unsignedUserUuid)
                .build());
        return new BaseResponse<>();
    }

}

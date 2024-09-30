package com.chicchoc.sivillage.domain.cart.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartService;
import com.chicchoc.sivillage.domain.cart.dto.in.CartMigrateRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import com.chicchoc.sivillage.domain.cart.vo.in.*;
import com.chicchoc.sivillage.domain.cart.vo.out.CartResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.jwt.util.JwtUtil;
import com.nimbusds.jwt.JWT;
import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
@Slf4j
public class CartController {

    private final CartService cartService;

    @Operation(summary = "createCart API", description = "장바구니 생성", tags = {"장바구니"})
    @PostMapping
    public BaseResponse<Void> createCart(@AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader("X-Unsigned-User-UUID") String unsignedUserUuid,
            @RequestBody CartRequestVo cartRequestVo
    ) {

        cartService.createCart(cartRequestVo.toDto(JwtUtil.getUserIdentifier(userDetails, unsignedUserUuid)));

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "getCart API", description = "장바구니 조회", tags = {"장바구니"})
    @GetMapping
    public BaseResponse<List<CartResponseVo>> getCart(@AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader("X-Unsigned-User-UUID") String unsignedUserUuid
    ) {

        return new BaseResponse<>(
                cartService.getCart(JwtUtil.getUserIdentifier(userDetails, unsignedUserUuid)).stream()
                        .map(CartResponseDto::toVo).toList()
        );
    }

    @Operation(summary = "updateCartItem API", description = "장바구니 상품 수정", tags = {"장바구니"})
    @PutMapping("/option/{cartUuid}")
    public BaseResponse<CartResponseVo> updateCartItem(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String cartUuid,
            @RequestBody CartUpdateRequestVo cartUpdateRequestVo) {

        return new BaseResponse<>(cartService.updateCartItem(
                cartUpdateRequestVo.toDto(userDetails.getUsername(), cartUuid)).toVo());
    }

    @Operation(summary = "updateItemQuantity API", description = "장바구니 상품 수량 수정", tags = {"장바구니"})
    @PutMapping("/quantity")
    public BaseResponse<Void> updateItemQuantity(
            @RequestBody ItemQuantityUpdateRequestVo itemQuantityUpdateRequestVo) {

        cartService.updateItemQuantity(itemQuantityUpdateRequestVo.toDto());

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "updateItemIsSelected API", description = "장바구니 상품 선택 여부 수정", tags = {"장바구니"})
    @PutMapping("/isSelected")
    public BaseResponse<Void> updateItemIsSelected(
            @RequestBody List<ItemIsSelectedUpdateRequestVo> itemIsSelectedUpdateRequestVoList) {

        cartService.updateItemIsSelected(itemIsSelectedUpdateRequestVoList.stream().map(
                ItemIsSelectedUpdateRequestVo::toDto
        ).toList());

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "deleteCartItems API", description = "장바구니 상품 삭제", tags = {"장바구니"})
    @DeleteMapping
    public BaseResponse<Void> deleteCartItems(@RequestBody List<CartDeleteRequestVo> cartDeleteRequestVoList) {
        cartService.deleteCartItems(cartDeleteRequestVoList.stream()
                .map(CartDeleteRequestVo::toDto)
                .toList());
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "migrateCart API", description = "장바구니 이전", tags = {"장바구니"})
    @PostMapping("/migrate")
    public BaseResponse<Void> migrateCart(@AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Unsigned-User-UUID", required = false)
            String unsignedUserUuid
    ) {
        cartService.migrateCart(CartMigrateRequestDto.builder()
                .userUuid(userDetails.getUsername())
                .unsignedUserUuid(unsignedUserUuid)
                .build());
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

}

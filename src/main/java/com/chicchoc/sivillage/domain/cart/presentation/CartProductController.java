package com.chicchoc.sivillage.domain.cart.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartProductService;
import com.chicchoc.sivillage.domain.cart.dto.in.CartProductRequestDto;
import com.chicchoc.sivillage.domain.cart.dto.out.CartProductResponseDto;
import com.chicchoc.sivillage.domain.cart.vo.in.CartProductRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.out.CartProductResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cartProduct")
public class CartProductController {

    private final CartProductService cartProductService;

    @PostMapping
    public BaseResponse<Void> addToCart(@RequestBody CartProductRequestVo cartProductRequestVo) {

        cartProductService.addToCart(cartProductRequestVo.toDto());

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @GetMapping("/{cartUuid}")
    public BaseResponse<List<CartProductResponseVo>> getCartItem(@PathVariable("cartUuid") String cartUuid) {
        List<CartProductResponseDto> cartProductResponseDtoList = cartProductService.getCartItem(cartUuid);
        return new BaseResponse<>(cartProductResponseDtoList.stream().map(CartProductResponseDto::toVo).toList());
    }

//    @PutMapping("/option/{cartUuid}")
//    public BaseResponse<CartResponseVo> updateCartItem(@PathVariable String cartUuid,
//            @RequestBody CartUpdateRequestVo cartUpdateRequestVo) {
//
//        return new BaseResponse<>(cartService.updateCartItem(cartUpdateRequestVo.toDto(cartUuid)).toVo());
//    }
//
//    @PutMapping("/status")
//    public BaseResponse<Void> updateCartStatus(
//            @RequestBody List<CartStatusUpdateRequestVo> cartUpdateStatusRequestVoList) {
//
//        cartService.updateCartStatus(cartUpdateStatusRequestVoList.stream()
//                .map(CartStatusUpdateRequestVo::toDto)
//                .toList());
//
//        return new BaseResponse<>();
//    }
//
//    @DeleteMapping
//    public BaseResponse<Void> deleteCartItems(@RequestBody List<CartDeleteRequestVo> cartDeleteRequestVoList) {
//        cartService.deleteCartItems(cartDeleteRequestVoList.stream()
//                .map(CartDeleteRequestVo::toDto)
//                .toList());
//        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
//    }
//
//    @PostMapping("/migrate")
//    public BaseResponse<Void> migrateCart(@AuthenticationPrincipal UserDetails userDetails,
//            @RequestHeader(value = "X-Unsigned-User-UUID", required = false) String unsignedUserUuid
//    ) {
//        cartService.migrateCart(CartMigrateRequestDto.builder()
//                .userUuid(userDetails.getUsername())
//                .unsignedUserUuid(unsignedUserUuid)
//                .build());
//        return new BaseResponse<>();
//    }
}

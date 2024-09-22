package com.chicchoc.sivillage.domain.cart.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartProductService;
import com.chicchoc.sivillage.domain.cart.dto.out.CartProductResponseDto;
import com.chicchoc.sivillage.domain.cart.vo.in.CartProductDeleteRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.in.CartProductOptionUpdateRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.in.CartProductRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.in.CartProductStatusUpdateRequestVo;
import com.chicchoc.sivillage.domain.cart.vo.out.CartProductResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping
    public BaseResponse<Void> deleteCartItems(@RequestBody List<CartProductDeleteRequestVo> cartDeleteRequestVoList) {
        cartProductService.deleteCartItems(cartDeleteRequestVoList.stream()
                .map(CartProductDeleteRequestVo::toDto)
                .toList());
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @PutMapping("/productOption/{cartProductUuid}")
    public BaseResponse<CartProductResponseVo> updateCartProductOption(
            @PathVariable("cartProductUuid") String cartProductUuid,
            @RequestBody CartProductOptionUpdateRequestVo cartProductOptionUpdateRequestVo) {

        return new BaseResponse<>(cartProductService.updateCartProductOption(
                cartProductOptionUpdateRequestVo.toDto(cartProductUuid)).toVo());
    }

    @PutMapping("/productStatus/{cartProductUuid}")
    public BaseResponse<CartProductResponseVo> updateCartProductStatus(
            @PathVariable("cartProductUuid") String cartProductUuid,
            @RequestBody CartProductStatusUpdateRequestVo cartProductStatusUpdateRequestVo) {

        return new BaseResponse<>(
                cartProductService.updateCartProductStatus(cartProductStatusUpdateRequestVo.toDto(cartProductUuid))
                        .toVo());
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

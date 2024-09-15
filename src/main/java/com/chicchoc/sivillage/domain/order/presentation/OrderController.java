package com.chicchoc.sivillage.domain.order.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartService;
import com.chicchoc.sivillage.domain.order.application.OrderService;
import com.chicchoc.sivillage.domain.order.dto.in.CartUuidRequestDto;
import com.chicchoc.sivillage.domain.order.dto.in.OrderProductRequestDto;
import com.chicchoc.sivillage.domain.order.dto.in.OrderRequestDto;
import com.chicchoc.sivillage.domain.order.dto.out.OrderResponseDto;
import com.chicchoc.sivillage.domain.order.vo.in.CartUuidRequestVo;
import com.chicchoc.sivillage.domain.order.vo.in.OrderProductRequestVo;
import com.chicchoc.sivillage.domain.order.vo.in.OrderRequestVo;
import com.chicchoc.sivillage.domain.order.vo.out.OrderResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @PostMapping
    public BaseResponse<Void> createOrder(Authentication authentication, @RequestBody OrderRequestVo orderRequestVo) {

        OrderRequestDto orderRequestDto = orderRequestVo.toDto();

        List<OrderProductRequestVo> orderProductRequestVoList = orderRequestVo.getOrderProductRequestVoList();
        List<OrderProductRequestDto> orderProductRequestDtoList = orderProductRequestVoList.stream()
                .map(OrderProductRequestVo::toDto)
                .toList();

        if (orderRequestVo.getCartUuidRequestVoList() != null && !orderRequestVo.getCartUuidRequestVoList().isEmpty()) {
            List<CartUuidRequestVo> cartUuidRequestVoList = orderRequestVo.getCartUuidRequestVoList();
            List<CartUuidRequestDto> cartUuidRequestDtoList = cartUuidRequestVoList.stream()
                    .map(CartUuidRequestVo::toDto)
                    .toList();

            cartService.deleteCartItems(cartUuidRequestDtoList);
        }

        String userUuid = authentication.getName();
        orderService.createOrder(orderRequestDto, orderProductRequestDtoList, userUuid);

        return new BaseResponse<>();
    }

    @GetMapping
    public BaseResponse<List<OrderResponseVo>> getOrders(Authentication authentication,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        List<OrderResponseDto> orderResponseDtoList = orderService.getOrder(authentication.getName(), startDate,
                endDate);

        List<OrderResponseVo> orderResponseVoList = orderResponseDtoList.stream()
                .map(OrderResponseDto::toVo)
                .toList();

        return new BaseResponse<>(orderResponseVoList);
    }

    // @GetMapping("/details")
    // public BaseResponse<OrderResponseVo> getOrderDetails(Authentication authentication) {

    // }
}

package com.chicchoc.sivillage.domain.order.presentation;

import com.chicchoc.sivillage.domain.cart.application.CartService;
import com.chicchoc.sivillage.domain.order.application.OrderService;
import com.chicchoc.sivillage.domain.order.dto.in.CartUuidRequestDto;
import com.chicchoc.sivillage.domain.order.dto.in.OrderProductRequestDto;
import com.chicchoc.sivillage.domain.order.dto.in.OrderRequestDto;
import com.chicchoc.sivillage.domain.order.dto.out.OrderDetailResponseDto;
import com.chicchoc.sivillage.domain.order.dto.out.OrderResponseDto;
import com.chicchoc.sivillage.domain.order.vo.in.CartUuidRequestVo;
import com.chicchoc.sivillage.domain.order.vo.in.OrderProductRequestVo;
import com.chicchoc.sivillage.domain.order.vo.in.OrderRequestVo;
import com.chicchoc.sivillage.domain.order.vo.out.OrderDetailResponseVo;
import com.chicchoc.sivillage.domain.order.vo.out.OrderResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public BaseResponse<Void> createOrder(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderRequestVo orderRequestVo) {

        if (orderRequestVo.getCartUuidRequestVoList() != null && !orderRequestVo.getCartUuidRequestVoList().isEmpty()) {
            List<CartUuidRequestVo> cartUuidRequestVoList = orderRequestVo.getCartUuidRequestVoList();
            List<CartUuidRequestDto> cartUuidRequestDtoList = cartUuidRequestVoList.stream()
                    .map(CartUuidRequestVo::toDto)
                    .toList();
            // 로직 검사 예정 -> 장바구니에 있는 데이터 주문하면 삭제되게
            // cartService.deleteCartItems(cartUuidRequestDtoList);
        }

        orderService.createOrder(orderRequestVo.toDto(userDetails.getUsername()),
                orderRequestVo.getOrderProductRequestVoList().stream()
                        .map(OrderProductRequestVo::toDto)
                        .toList());

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
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

    @GetMapping("/{orderUuid}")
    public BaseResponse<OrderDetailResponseVo> getOrderDetails(Authentication authentication,
            @PathVariable("orderUuid") String orderUuid) {
        OrderDetailResponseDto orderDetailResponseDto = orderService.getOrderDetail(orderUuid);
        OrderDetailResponseVo orderDetailResponseVo = orderDetailResponseDto.toVo();
        return new BaseResponse<>(orderDetailResponseVo);
    }

    @DeleteMapping("/{orderUuid}")
    public BaseResponse<Void> deleteOrder(Authentication authentication, @PathVariable("orderUuid") String orderUuid) {
        orderService.deleteOrder(orderUuid);
        return new BaseResponse<>();
    }
}

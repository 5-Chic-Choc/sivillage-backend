package com.chicchoc.sivillage.domain.order.presentation;

import com.chicchoc.sivillage.domain.order.application.OrderService;
import com.chicchoc.sivillage.domain.order.dto.in.OrderProductRequestDto;
import com.chicchoc.sivillage.domain.order.dto.in.OrderRequestDto;
import com.chicchoc.sivillage.domain.order.vo.in.OrderProductRequestVo;
import com.chicchoc.sivillage.domain.order.vo.in.OrderRequestVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public BaseResponse<Void> createOrder(Authentication authentication, @RequestBody OrderRequestVo orderRequestVo) {

        OrderRequestDto orderRequestDto = orderRequestVo.toDto();
        List<OrderProductRequestVo> orderProductRequestVoList = orderRequestVo.getOrderProductRequestVoList();
        List<OrderProductRequestDto> orderProductRequestDtoList = orderProductRequestVoList.stream()
                .map(OrderProductRequestVo::toDto)
                .toList();

        String userUuid = authentication.getName();
        orderService.createOrder(orderRequestDto, orderProductRequestDtoList, userUuid);

        return new BaseResponse<>();
    }

}

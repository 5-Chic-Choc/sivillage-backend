package com.chicchoc.sivillage.domain.order.presentation;

import com.chicchoc.sivillage.domain.order.application.OrderService;
import com.chicchoc.sivillage.domain.order.vo.in.OrderRequestVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public BaseResponse<Void> createOrder(@RequestBody OrderRequestVo orderRequestVo) {


        return new BaseResponse<>();
    }

}

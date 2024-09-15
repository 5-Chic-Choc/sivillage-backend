package com.chicchoc.sivillage.domain.order.application;

import com.chicchoc.sivillage.domain.order.dto.in.OrderProductRequestDto;
import com.chicchoc.sivillage.domain.order.dto.in.OrderRequestDto;
import java.util.List;

public interface OrderService {
    void createOrder(OrderRequestDto orderRequestDto, List<OrderProductRequestDto> orderProductRequestDtoList, String userUuid);
}

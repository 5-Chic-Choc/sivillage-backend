package com.chicchoc.sivillage.domain.order.application;

import com.chicchoc.sivillage.domain.order.dto.in.OrderProductRequestDto;
import com.chicchoc.sivillage.domain.order.dto.in.OrderRequestDto;
import com.chicchoc.sivillage.domain.order.dto.out.OrderDetailResponseDto;
import com.chicchoc.sivillage.domain.order.dto.out.OrderResponseDto;
import com.chicchoc.sivillage.domain.order.vo.out.OrderResponseVo;
import java.util.Date;
import java.util.List;

public interface OrderService {

    void createOrder(OrderRequestDto orderRequestDto, List<OrderProductRequestDto> orderProductRequestDtoList,
            String userUuid);

    List<OrderResponseDto> getOrder(String userUuid, String startDate, String endDate);

    OrderDetailResponseDto getOrderDetail(String orderUuid);

    void deleteOrder(String orderUuid);
}

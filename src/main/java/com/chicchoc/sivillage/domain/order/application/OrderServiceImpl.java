package com.chicchoc.sivillage.domain.order.application;

import com.chicchoc.sivillage.domain.order.domain.Order;
import com.chicchoc.sivillage.domain.order.domain.OrderProduct;
import com.chicchoc.sivillage.domain.order.dto.in.OrderProductRequestDto;
import com.chicchoc.sivillage.domain.order.dto.in.OrderRequestDto;
import com.chicchoc.sivillage.domain.order.infrastructure.OrderProductRepository;
import com.chicchoc.sivillage.domain.order.infrastructure.OrderRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final NanoIdGenerator nanoIdGenerator;

    @Override
    public void createOrder(OrderRequestDto orderRequestDto, List<OrderProductRequestDto> orderProductRequestDtoList,
            String userUuid) {

        String orderUuid = nanoIdGenerator.generateNanoId();
        String paymentUuid = nanoIdGenerator.generateNanoId();
        LocalDateTime orderDate = LocalDateTime.now();
        String deliveryCompany = "Test Company";
        String trackingNumber = "Test Tracking";

        List<OrderProduct> orderProductEntities = orderProductRequestDtoList.stream()
                .map(orderProductRequestDto -> orderProductRequestDto.toEntity(orderUuid))
                .toList();

        orderRepository.save(
                orderRequestDto.toEntity(orderUuid, userUuid, paymentUuid, orderDate, deliveryCompany, trackingNumber));

        orderProductRepository.saveAll(orderProductEntities);
    }
}

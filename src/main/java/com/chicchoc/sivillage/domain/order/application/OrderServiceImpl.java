package com.chicchoc.sivillage.domain.order.application;

import com.chicchoc.sivillage.domain.order.domain.OrderProduct;
import com.chicchoc.sivillage.domain.order.dto.in.OrderProductRequestDto;
import com.chicchoc.sivillage.domain.order.dto.in.OrderRequestDto;
import com.chicchoc.sivillage.domain.order.dto.out.OrderResponseDto;
import com.chicchoc.sivillage.domain.order.infrastructure.OrderProductRepository;
import com.chicchoc.sivillage.domain.order.infrastructure.OrderRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @Override
    public List<OrderResponseDto> getOrder(String userUuid, String startDate, String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);

        List<String> orderUuidList = orderRepository.findOrderUuidByDateRangeWithUserUuid(userUuid, start, end);

        List<OrderProduct> orderProductList = new ArrayList<>();

        for (String orderUuid : orderUuidList) {
            List<OrderProduct> productsForOrder = orderProductRepository.findByOrderUuid(orderUuid);
            orderProductList.addAll(productsForOrder);
        }

        return orderProductList.stream()
                .map(orderProduct -> OrderResponseDto.builder()
                        .orderUuid(orderProduct.getOrderUuid())
                        .productUuid(orderProduct.getProductUuid())
                        .productName(orderProduct.getProductName())
                        .brandName(orderProduct.getBrandName())
                        .productPrice(orderProduct.getProductPrice())
                        .discountedPrice(orderProduct.getDiscountedPrice())
                        .colorValue(orderProduct.getColorValue())
                        .sizeName(orderProduct.getSizeName())
                        .productOption(orderProduct.getProductOption())
                        .amount(orderProduct.getAmount())
                        .thumbnailUrl(orderProduct.getThumbnailUrl())
                        .createdAt(orderProduct.getCreatedAt())
                        .updatedAt(orderProduct.getUpdatedAt())
                        .build())
                .toList();

    }
}

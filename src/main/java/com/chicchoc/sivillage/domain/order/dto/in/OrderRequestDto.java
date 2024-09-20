package com.chicchoc.sivillage.domain.order.dto.in;

import com.chicchoc.sivillage.domain.order.domain.Order;
import com.chicchoc.sivillage.domain.order.domain.OrderStatus;
import com.chicchoc.sivillage.domain.order.vo.in.OrderProductRequestVo;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {

    private String userUuid;
    private String orderUuid;
    private String ordererName;
    private String ordererEmail;
    private String ordererPhone;
    private String postalCode;
    private String recipientAddress;
    private String recipientName;
    private String recipientPhone;
    private String deliveryName;
    private String deliveryRequest;
    private OrderStatus orderStatus;
    private List<OrderProductRequestDto> orderProductRequestDtoList;

    @Builder
    public OrderRequestDto(String userUuid, String ordererName, String ordererEmail, String ordererPhone,
            String postalCode, String recipientAddress, String recipientName, String recipientPhone,
            String deliveryName, String deliveryRequest) {
        this.userUuid = userUuid;
        this.orderUuid = NanoIdGenerator.generateNanoId();
        this.ordererName = ordererName;
        this.ordererEmail = ordererEmail;
        this.ordererPhone = ordererPhone;
        this.postalCode = postalCode;
        this.recipientAddress = recipientAddress;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.deliveryName = deliveryName;
        this.deliveryRequest = deliveryRequest;
        this.orderStatus = OrderStatus.PENDING;
    }

    public Order toEntity(String paymentUuid, LocalDateTime orderedAt) {
        return Order.builder()
                .userUuid(userUuid)
                .orderUuid(orderUuid)
                .userUuid(userUuid)
                .paymentUuid(paymentUuid)
                .orderedAt(orderedAt)
                .orderStatus(orderStatus)
                .ordererName(ordererName)
                .ordererEmail(ordererEmail)
                .ordererPhone(ordererPhone)
                .postalCode(postalCode)
                .recipientAddress(recipientAddress)
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
                .deliveryName(deliveryName)
                .deliveryRequest(deliveryRequest)
                .build();
    }
}


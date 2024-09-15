package com.chicchoc.sivillage.domain.order.dto.in;

import com.chicchoc.sivillage.domain.order.domain.Order;
import com.chicchoc.sivillage.domain.order.vo.in.OrderProductRequestVo;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private String ordererName;
    private String ordererEmail;
    private String ordererPhone;
    private String postalCode;
    private String recipientAddress;
    private String recipientName;
    private String recipientPhone;
    private String deliveryName;
    private String deliveryRequest;
    private List<OrderProductRequestDto> orderProductRequestDtoList;

    public Order toEntity(String orderUuid, String userUuid, String paymentUuid, LocalDateTime orderedAt,
            String deliveryCompany, String trackingNumber) {
        return Order.builder()
                .orderUuid(orderUuid)
                .userUuid(userUuid)
                .paymentUuid(paymentUuid)
                .orderedAt(orderedAt)
                .ordererName(ordererName)
                .ordererEmail(ordererEmail)
                .ordererPhone(ordererPhone)
                .postalCode(postalCode)
                .recipientAddress(recipientAddress)
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
                .deliveryName(deliveryName)
                .deliveryRequest(deliveryRequest)
                .deliveryCompany(deliveryCompany)
                .trackingNumber(trackingNumber)
                .build();
    }
}


package com.chicchoc.sivillage.domain.order.dto.out;

import com.chicchoc.sivillage.domain.order.vo.out.OrderDetailResponseVo;

public class OrderDetailResponseDto {
    private String ordererName;
    private String ordererEmail;
    private String ordererPhone;
    private String recipientName;
    private String recipientPhone;
    private String recipientAddress;
    private String deliveryName;
    private String deliveryRequest;

    public OrderDetailResponseVo toVo(){
        return OrderDetailResponseVo.builder()
                .ordererName(ordererName)
                .ordererEmail(ordererEmail)
                .ordererPhone(ordererPhone)
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
                .recipientAddress(recipientAddress)
                .deliveryName(deliveryName)
                .deliveryRequest(deliveryRequest)
                .build();
    }
}

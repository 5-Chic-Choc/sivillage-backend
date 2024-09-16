package com.chicchoc.sivillage.domain.order.vo.in;

import com.chicchoc.sivillage.domain.order.dto.in.OrderRequestDto;
import java.util.List;
import lombok.Getter;


@Getter
public class OrderRequestVo {

    private String ordererName;
    private String ordererEmail;
    private String ordererPhone;
    private String postalCode;
    private String recipientName;
    private String recipientPhone;
    private String recipientAddress;
    private String deliveryName;
    private String deliveryRequest;
    private List<OrderProductRequestVo> orderProductRequestVoList;
    private List<CartUuidRequestVo> cartUuidRequestVoList;

    public OrderRequestDto toDto() {
        return OrderRequestDto.builder()
                .ordererName(ordererName)
                .ordererEmail(ordererEmail)
                .ordererPhone(ordererPhone)
                .postalCode(postalCode)
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
                .recipientAddress(recipientAddress)
                .deliveryName(deliveryName)
                .deliveryRequest(deliveryRequest)
                .build();

    }
}

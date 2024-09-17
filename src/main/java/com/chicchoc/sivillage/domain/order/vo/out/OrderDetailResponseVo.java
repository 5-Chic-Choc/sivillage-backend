package com.chicchoc.sivillage.domain.order.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponseVo {
    private String ordererName;
    private String ordererEmail;
    private String ordererPhone;
    private String recipientName;
    private String recipientPhone;
    private String recipientAddress;
    private String deliveryName;
    private String deliveryRequest;
}

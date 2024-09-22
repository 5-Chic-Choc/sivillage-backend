package com.chicchoc.sivillage.domain.payment.dto.in;

import lombok.Getter;

@Getter
public class PaymentRequestDto {

    private String paymentKey;
    private String orderUuid;
    private Integer amount;
}

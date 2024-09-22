package com.chicchoc.sivillage.domain.payment.application;

import com.chicchoc.sivillage.domain.payment.dto.in.PaymentRequestDto;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<String> requestPayment(PaymentRequestDto paymentRequestDto);
}

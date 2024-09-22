package com.chicchoc.sivillage.domain.payment.presentation;

import com.chicchoc.sivillage.domain.payment.application.PaymentService;
import com.chicchoc.sivillage.domain.payment.dto.in.PaymentRequestDto;
import com.chicchoc.sivillage.global.config.TossConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment/toss")
@RequiredArgsConstructor
public class PaymentController {

    private final TossConfig tossConfig;
    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<String> requestTossPayment(@RequestBody PaymentRequestDto paymentRequestDto) {

        return paymentService.requestPayment(paymentRequestDto);
    }

    @GetMapping("/success")
    public String paymentSuccess() {
        // 결제 성공 후 리다이렉트되는 URL 처리 로직
        return "결제가 성공적으로 처리되었습니다.";
    }

    public String paymentFail() {
        // 결제 실패 후 리다이렉트되는 URL 처리 로직
        return "결제에 실패하였습니다.";
    }
}

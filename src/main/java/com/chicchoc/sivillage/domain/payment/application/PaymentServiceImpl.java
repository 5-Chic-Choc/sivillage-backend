package com.chicchoc.sivillage.domain.payment.application;

import com.chicchoc.sivillage.domain.payment.dto.in.PaymentRequestDto;
import com.chicchoc.sivillage.global.config.TossConfig;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;
    private final TossConfig tossConfig;


    public ResponseEntity<String> requestPayment(PaymentRequestDto paymentRequestDto) {
        // 토스페이먼츠 API의 URL
        String url = tossConfig.getBaseUrl() + "/payments/confirm";

        // Secret Key를 Base64로 인코딩
        String encodedSecretKey =
                "Basic " + Base64.getEncoder().encodeToString((tossConfig.getTestSecretKey() + ":").getBytes());

        System.out.println(encodedSecretKey);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", encodedSecretKey);

        // 결제 승인 요청 바디 생성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("paymentKey", paymentRequestDto.getPaymentKey());
        requestBody.put("orderId", paymentRequestDto.getOrderUuid());
        requestBody.put("amount", paymentRequestDto.getAmount());

        // HttpEntity 생성
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // POST 요청으로 결제 승인
            return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (HttpClientErrorException e) {
            // 결제 실패 시 에러 처리
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

}

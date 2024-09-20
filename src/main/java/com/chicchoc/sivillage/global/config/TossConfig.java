package com.chicchoc.sivillage.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TossConfig {
    @Value("${toss.payments.test-client-key}")
    private String testClientKey;

    @Value("${toss.payments.test-secret-key}")
    private String testSecretKey;

    @Value("${toss.payments.success-url}")
    private String successUrl;

    @Value("${toss.payments.fail-url}")
    private String failUrl;

    public static final String URL = "https://api.tosspayments.com/v1";
}

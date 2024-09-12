package com.chicchoc.sivillage.global.common.generator;

public class VerificationCode {

    public static String generateCode() {

        StringBuilder verificationCode = new StringBuilder();

        // 6자리의 인증 코드 생성
        for (int i = 0; i < 6; i++) {
            verificationCode.append((int) (Math.random() * 10));
        }
        return verificationCode.toString();
    }
}

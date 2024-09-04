package com.chicchoc.sivillage.global.auth.exception;

import java.util.Map;
import lombok.Getter;

@Getter
public class ValidException extends RuntimeException {
    // RuntimeException을 상속받아 커스텀 예외 클래스 생성

    private Map<String, String> errorMap;

    public ValidException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

}

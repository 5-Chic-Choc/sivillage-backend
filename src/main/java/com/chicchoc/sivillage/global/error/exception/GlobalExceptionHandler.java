package com.chicchoc.sivillage.global.error.exception;

import com.chicchoc.sivillage.global.auth.exception.UnknownException;
import com.chicchoc.sivillage.global.auth.exception.ValidException;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //todo : 예외 처리 관련 Team Rule 정리

    // IllegalArgumentException 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResponseEntity<?> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        log.error("IllegalArgumentException 발생: {}", ex.getMessage());

        return new CommonResponseEntity<>(
                HttpStatus.BAD_REQUEST,  // 400
                ex.getMessage(),
                null
        );
    }

    // UnKnownException 예외 처리
    @ExceptionHandler(UnknownException.class)
    public CommonResponseEntity<?> handleDatabaseException(Exception ex) {

        return new CommonResponseEntity<>(
                HttpStatus.INTERNAL_SERVER_ERROR,  // 500
                "서버에서 오류가 발생했습니다. 관리자에게 문의하세요.",
                null
        );
    }

    // ValidException 예외 처리
    @ExceptionHandler(ValidException.class)
    public CommonResponseEntity<?> handleValidException(ValidException validException) {
        // 예외 발생 시 로그 기록
        log.error("ValidException 발생: {}", validException.getMessage());

        // CommonResponseEntity 객체 생성 및 반환
        return new CommonResponseEntity<>(
                HttpStatus.BAD_REQUEST,  // 400
                validException.getMessage(),
                validException.getErrorMap()
        );
    }
}

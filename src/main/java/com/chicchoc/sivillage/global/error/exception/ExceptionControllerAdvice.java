package com.chicchoc.sivillage.global.error.exception;

import com.chicchoc.sivillage.global.auth.exception.ValidException;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

  // IllegalArgumentException 예외 처리
  @ExceptionHandler(IllegalArgumentException.class)
  public CommonResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
    log.error("IllegalArgumentException 발생: {}", illegalArgumentException.getMessage());

    return new CommonResponseEntity<>(
        HttpStatus.BAD_REQUEST,  // 400 Bad Request 상태 코드
        illegalArgumentException.getMessage(),  // 예외 메시지
        null  // 데이터 없음
    );
  }

  // MethodArgumentNotValidException 예외 처리
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public CommonResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return new CommonResponseEntity<>(
        HttpStatus.BAD_REQUEST,
        "유효성 검사 오류:MethodArgumentNotValidException",
        errors
    );
  }

  // ValidException 예외 처리
  @ExceptionHandler(ValidException.class)
  public CommonResponseEntity<?> handleValidException(ValidException validException) {
    // 예외 발생 시 로그 기록
    log.error("ValidException 발생: {}", validException.getMessage());

    // CommonResponseEntity 객체 생성 및 반환
    return new CommonResponseEntity<>(
        HttpStatus.BAD_REQUEST,  // 400 Bad Request 상태 코드
        validException.getMessage(),  // 예외 메시지
        validException.getErrorMap()  // 데이터 대신 에러 맵 반환
    );
  }
}

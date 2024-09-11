package com.chicchoc.sivillage.global.error.exception;

import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    /*
     * Controller 에서 발생한 예외 처리 클래스
     * (Serivce에서 발생한 예외 또한 Controller에서 처리)
     */

    /**
     * 발생한 예외 처리.
     */
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<BaseResponse<Void>> baseError(BaseException e) {

        BaseResponse<Void> response = new BaseResponse<>(e.getStatus());

        log.error("예외 발생: {}({}), 값 : {}", e.getStatus(), e.getStatus().getMessage(), e.getMessage());

        return new ResponseEntity<>(response, response.httpStatus());
    }

    /**
     * security 인증 에러 아이디가 없거나 비밀번호가 틀린 경우 AuthenticationManager 에서 발생.
     *
     * @return FAILED_TO_LOGIN 에러 response
     */
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<BaseResponse<Void>> handleBadCredentialsException(BadCredentialsException e) {

        BaseResponse<Void> response = new BaseResponse<>(BaseResponseStatus.FAILED_TO_LOGIN);
        log.error("BadCredentialsException: ", e);

        return new ResponseEntity<>(response, response.httpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<BaseResponse<Void>> runtimeError(RuntimeException e) {

        BaseResponse<Void> response = new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        log.error("RuntimeException: ", e);

        for (StackTraceElement s : e.getStackTrace()) {
            System.out.println(s);
        }

        return new ResponseEntity<>(response, response.httpStatus());
    }

    // IllegalArgumentException 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Void>> handleIllegalArgumentException(
            IllegalArgumentException e) {

        BaseResponse<Void> response = new BaseResponse<>(BaseResponseStatus.ILLEGAL_ARGUMENT);
        log.error("IllegalArgumentException : {}", e.getMessage());

        return new ResponseEntity<>(response, response.httpStatus());
    }

    // Database 관련 예외 처리
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<BaseResponse<Void>> handleDatabaseException(DataAccessException e) {

        BaseResponse<Void> response = new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        log.error("DataAccessException : {}", e.getMessage());

        return new ResponseEntity<>(response, response.httpStatus());
    }

    // 요청 값 유효성 검사 실패시 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<String>> handleMethodArgsNotValidException(MethodArgumentNotValidException e) {

        log.error("MethodArgumentNotValidException 발생: {}", e.getMessage());

        // DTO단 에서 지정한 message 값을 가져옴(없을 경우 기본 메시지)
        BaseResponseStatus defaultStatus = BaseResponseStatus.INVALID_INPUT_VALUE;
        String errorMessage = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage() :
                defaultStatus.getMessage();

        BaseResponse<String> response = new BaseResponse<>(
                defaultStatus.getHttpStatusCode(),
                false,
                errorMessage, // DTO 에서 설정한 메시지를 출력하도록 함
                defaultStatus.getCode(),
                null
        );

        return new ResponseEntity<>(response, response.httpStatus());
    }
}
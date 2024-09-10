package com.chicchoc.sivillage.global.error.exception;

import com.chicchoc.sivillage.global.auth.exception.UnknownException;
import com.chicchoc.sivillage.global.auth.exception.ValidException;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    //todo : 예외 처리 관련 Team Rule 정리

    /**
     * 발생한 예외 처리.
     */
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<BaseResponse<Void>> baseError(BaseException e) {
        BaseResponse<Void> response = new BaseResponse<>(e.getStatus());
        log.error("BaseException -> {}({})", e.getStatus(), e.getStatus().getMessage(), e);
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

    // ====================================================================

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

    // MethodArgsNotValidException 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponseEntity<?> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        // 예외 발생 시 로그 기록
        log.error("MethodArgsNotValidException 발생: {}", ex.getMessage());

        // CommonResponseEntity 객체 생성 및 반환
        return new CommonResponseEntity<>(
                HttpStatus.BAD_REQUEST,  // 400
                ex.getMessage(),
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
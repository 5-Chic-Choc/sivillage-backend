package com.chicchoc.sivillage.global.auth.exception;

import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //AuthenticationEntryPoint : 인증이 필요한 리소스에 접근하려고 할 때 인증을 하지 않았을 때 발생하는 예외를 처리하는 클래스
    //시큐리티의 모든 예외는 여기(CAEP)에서 처리함.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws ServletException, IOException {

        log.error("CustomAuthenticationEntryPoint 예외 : {}", authException.getMessage());

        // 응답 타입 설정 (JSON 형태)
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // 에러 상태 및 메시지 설정
        BaseResponseStatus status = getErrorStatus(authException);
        BaseResponse<Object> errorResponse = new BaseResponse<>(status);

        // JSON 변환 후 응답
        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(responseJson);
        response.setStatus(status.getHttpStatusCode().value());
    }

    // 주어진 AuthenticationException에 따라 적절한 오류 상태를 반환
    private BaseResponseStatus getErrorStatus(AuthenticationException authException) {
        if (authException.getClass() == BadCredentialsException.class) {
            return BaseResponseStatus.FAILED_TO_LOGIN;  // 잘못된 비밀번호
        } else if (authException.getClass() == UsernameNotFoundException.class) {
            return BaseResponseStatus.NO_EXIST_USER;  // 존재하지 않는 사용자
        } else if (authException.getClass() == AccountExpiredException.class) {
            return BaseResponseStatus.DISABLED_USER;  // 계정 만료
        } else if (authException.getClass() == CredentialsExpiredException.class) {
            return BaseResponseStatus.INTERNAL_SERVER_ERROR;  // 인증서 만료
        } else if (authException.getClass() == DisabledException.class) {
            return BaseResponseStatus.DISABLED_USER;  // 계정 비활성화
        } else if (authException.getClass() == LockedException.class) {
            return BaseResponseStatus.NO_ACCESS_AUTHORITY;  // 계정 잠금
        } else {
            return BaseResponseStatus.NO_SIGN_IN;  // 일반적인 인증 실패
        }
    }
}
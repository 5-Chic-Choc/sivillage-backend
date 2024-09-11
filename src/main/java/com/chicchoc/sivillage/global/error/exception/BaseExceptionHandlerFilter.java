package com.chicchoc.sivillage.global.error.exception;

import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class BaseExceptionHandlerFilter extends OncePerRequestFilter {

    // BaseException을 처리하는 필터
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BaseException e) {
            log.error("BaseExceptionHandlerFilter에서 예외 발생: {}({}) 값:{}",
                    e.getStatus(), e.getStatus().getMessage(), e.getMessage());
            setErrorResponse(response, e);
        } // 인증 예외는 CustomAuthenticationEntryPoint에서 처리
    }


    private void setErrorResponse(HttpServletResponse response,
            BaseException be) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        BaseResponse baseResponse = new BaseResponse(be.getStatus());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
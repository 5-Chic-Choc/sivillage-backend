package com.chicchoc.sivillage.global.auth.exception;

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

        log.error("CustomAuthenticationEntryPoint 예외 : " + authException.getMessage());

        //응답코드 401, JSON형태로 응답
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        // 예외 메시지를 담을 맵 생성
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", getErrorMessage(authException));

        //맵을 JSON으로 변환
        JsonMapper jsonMapper = new JsonMapper();
        String responseJson = jsonMapper.writeValueAsString(errorMap);

        //응답
        response.getWriter().println(responseJson);
    }

  // 주어진 AuthenticationException에 따라 적절한 오류 메시지를 반환함
  private String getErrorMessage(AuthenticationException authException) {
    if (authException.getClass() == BadCredentialsException.class) {
      return "BadCredentialsException 잘못된 비밀번호입니다. 다시 확인하세요.";
    } else if (authException.getClass() == UsernameNotFoundException.class) {
      return "UsernameNotFoundException 가입되지 않은 이메일입니다. 다시 확인하세요.";
    } else if (authException.getClass() == AccountExpiredException.class) {
      return "AccountExpiredException 계정이 만료되었습니다. 관리자에게 문의하세요.";
    } else if (authException.getClass() == CredentialsExpiredException.class) {
      return "CredentialsExpiredException 인증서가 만료되었습니다. 관리자에게 문의하세요.";
    } else if (authException.getClass() == DisabledException.class) {
      return "DisabledException 비활성화된 계정입니다. 관리자에게 문의하세요.";
    } else if (authException.getClass() == LockedException.class) {
      return "LockedException 계정이 잠겼습니다. 관리자에게 문의하세요.";
    } else {
      return "인증에 실패했습니다. 다시 시도하세요.";
    }


}

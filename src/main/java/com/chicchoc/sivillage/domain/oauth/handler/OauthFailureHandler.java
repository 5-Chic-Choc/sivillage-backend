package com.chicchoc.sivillage.domain.oauth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OauthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        // 실패 원인을 로그로 남기고, 프론트엔드에서 알 수 있도록 리다이렉트
        String errorMessage = exception.getMessage();
        log.error("OAuth2 인증 실패: {}", errorMessage);

        // 인증 실패 페이지로 리다이렉트
        response.sendRedirect("/auth/oauth/failure?error=" + errorMessage);
    }
}

package com.chicchoc.sivillage.global.jwt.config;

import com.chicchoc.sivillage.global.jwt.application.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAutenticationFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(jwtProperties.getAccessTokenPrefix());

        // 헤더에 토큰이 없거나, 토큰 접두사(Bearer) 가 아닌 경우 필터를 통과
        if (authHeader == null || !authHeader.startsWith(jwtProperties.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 요청에서 토큰이 잘 들어오는지 로그

        String token = getAccessToken(authHeader);
        boolean validToken = jwtTokenProvider.isValidToken(token);
        boolean isAuthenticated = SecurityContextHolder.getContext().getAuthentication() != null;

        // 토큰이 유효하고 인증되어 있지 않다면, 토큰을 이용해 인증 객체 생성 => SecurityContext에 저장
        if (validToken && !isAuthenticated) {
            Authentication authentication = jwtTokenProvider.createAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    // 헤더에서 토큰을 가져오는 메서드
    private String getAccessToken(String requestHeader) {

        String prefix = jwtProperties.getTokenPrefix();

        if (requestHeader != null && requestHeader.startsWith(prefix)) {
            return requestHeader.substring(prefix.length()).trim(); //공백까지 제거
        }
        return null;
    }
}

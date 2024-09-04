package com.chicchoc.sivillage.global.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtAutenticationFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    private String header;
    private String prefix;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        header = jwtProperties.getHeaderString();
        prefix = jwtProperties.getTokenPrefix();

        //헤더에서 토큰을 가져옴
        final String authHeader = request.getHeader(header);

        String token = getAccessToken(authHeader);

        // todo : UserDetailsService 방법과 비교
        // 유효하면 인증을 수행하고 SecurityContext에 저장
        if (jwtTokenProvider.validToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }

    // 헤더에서 토큰을 가져오는 메서드
    private String getAccessToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith(prefix)) {
            return authHeader.substring(prefix.length());
        }
        return null;
    }
}

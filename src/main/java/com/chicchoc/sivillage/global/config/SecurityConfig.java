package com.chicchoc.sivillage.global.config;

import com.chicchoc.sivillage.global.auth.exception.CustomAuthenticationEntryPoint;
import com.chicchoc.sivillage.global.auth.jwt.JwtAutenticationFilter;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAutenticationFilter jwtAutenticationFilter;

    @Bean
    public CorsFilter corsFilter() { //CORS 설정

        /* CORS 설정
        - allowCredentials: 쿠키를 주고 받을 수 있도록 설정
        - addAllowedOriginPattern: 모든 Origin 허용
        - addAllowedHeader: 모든 Header 허용
        - addAllowedMethod: 모든 Method 허용
        - setExposedHeaders: Authorization 헤더를 노출
        - registerCorsConfiguration: 모든 URL에 대해 CORS 설정 적용
        - UrlBasedCorsConfigurationSource: URL 기반의 CORS 설정을 적용하기 위한 클래스
        - source.registerCorsConfiguration("/**", config): 모든 URL에 대해 CORS 설정을 적용
        - CorsFilter: CORS 설정을 적용하기 위한 필터
        - return new CorsFilter(source): CORS 설정을 적용한 필터 반환
         */


        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setExposedHeaders(List.of("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 설정 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // 인증되지 않은 사용자가 접근할 수 있는 URL 설정
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests.requestMatchers("/api/v1/auth/**",
                                        "/api/v1/main/**", "/api/v1/products/**", "/swagger-ui/**",
                                        "/v3/api-docs/**", "/error").permitAll().anyRequest()
                                .authenticated())

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))

                // 인증되지 않은 사용자가 접근할 경우 CustomAuthenticationEntryPoint로 이동
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(
                        new CustomAuthenticationEntryPoint()))

                // 인증 처리를 위한 AuthenticationProvider 설정
                .authenticationProvider(authenticationProvider)

                // JWT 인증 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(jwtAutenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // CORS 설정 추가
                .addFilter(corsFilter());
        return http.build();
    }
}

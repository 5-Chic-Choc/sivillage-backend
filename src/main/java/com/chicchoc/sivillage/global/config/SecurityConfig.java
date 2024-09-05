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

    // CORS 설정, 인증 처리, JWT 인증 필터 설정
    private final AuthenticationProvider authenticationProvider;
    private final JwtAutenticationFilter jwtAutenticationFilter;

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration(); //CORS 설정
        config.setAllowCredentials(true); //쿠키를 주고 받을 수 있도록 설정
        config.addAllowedOriginPattern("*"); //모든 Origin 허용
        config.addAllowedHeader("Content-Type"); // JSON 데이터만 헤더로 받음
        config.addAllowedMethod("*"); //모든 Method 허용
        config.setExposedHeaders(List.of("Authorization")); //Authorization 헤더를 노출
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); //모든 URL에 대해 CORS 설정 적용
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 설정 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 설정 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 설정 비활성화
                .logout(AbstractHttpConfigurer::disable) // 로그아웃 설정 비활성화
                // 인증되지 않은 사용자가 접근할 수 있는 URL 설정
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                // 인증이 필요한 URL 설정 todo: 회원만 할 수 있는 작업은 추가 필요(ex:리뷰 작성)
                                .requestMatchers(
                                        "/api/v1/mypage/**",
                                        "/api/v1/order/**",
                                        "/api/v1/payment/**"
                                )
                                .authenticated()
                                // 위 URL 외의 요청은 인증 없이 접근 가능함
                                .anyRequest()
                                .permitAll()
                )
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
package com.chicchoc.sivillage.global.config;

import com.chicchoc.sivillage.domain.oauth.application.Oauth2UserServiceImpl;
import com.chicchoc.sivillage.domain.oauth.handler.OauthFailureHandler;
import com.chicchoc.sivillage.domain.oauth.handler.OauthSuccessHandler;
import com.chicchoc.sivillage.global.auth.exception.CustomAuthenticationEntryPoint;
import com.chicchoc.sivillage.global.jwt.config.JwtAutenticationFilter;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
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
    private final JwtProperties jwtProperties;

    private final Oauth2UserServiceImpl oauth2UserServiceImpl;
    private final OauthSuccessHandler oauth2SuccessHandler;
    private final OauthFailureHandler oauth2FailureHandler;

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration(); //CORS 설정
        config.setAllowCredentials(true); //쿠키를 주고 받을 수 있도록 설정
        // config.addAllowedOrigin("https://sivillage.shop");
        // config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("*"); //모든 Origin 허용(for 테스트)
        config.addAllowedHeader("*"); // 모든 Header 허용
        config.addAllowedMethod("*"); //모든 Method 허용
        config.setExposedHeaders(List.of(jwtProperties.getAccessTokenPrefix())); //Authorization 헤더를 노출
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); //모든 URL에 대해 CORS 설정 적용
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 설정 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // Form 로그인 설정 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 설정 비활성화
                .logout(AbstractHttpConfigurer::disable) // 로그아웃 설정 비활성화

                // 인증되지 않은 사용자가 접근할 수 있는 URL 설정
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                // 인증이 필요한 URL 설정 todo: 회원만 할 수 있는 작업은 추가 필요(ex:리뷰 작성)
                                .requestMatchers(
                                        "/api/v1/mypage/**",
                                        "/api/v1/order/**",
                                        "/api/v1/payment/**",
                                        "/api/v1/product/like",
                                        "/api/v1/promotion/like",
                                        "/api/v1/brand/like"
                                )
                                .authenticated()
                                // 위 URL 외의 요청은 인증 없이 접근 가능함
                                .anyRequest()
                                .permitAll()
                )

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))

                // oauth2
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(endpoint
                                -> endpoint.baseUri("/api/v1/auth/oauth2")) // oauth2 로그인 URL
                        .redirectionEndpoint(endpoint
                                -> endpoint.baseUri("/oauth2/callback/*")) // oauth2 로그인 후 리다이렉션 URL
                        .userInfoEndpoint(endpoint
                                -> endpoint.userService(oauth2UserServiceImpl)) // oauth2 사용자 정보 가져오기(이메일, 이름 등)
                        .successHandler(oauth2SuccessHandler) // oauth2 로그인 성공 핸들러(토큰 발급)
                        .failureHandler(oauth2FailureHandler))

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
package com.chicchoc.sivillage.global.config;

import com.chicchoc.sivillage.global.auth.application.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class ApplicationConfig {
    // 사용자 정보, 비밀번호 인코더, 인증 처리를 위한 빈을 생성하는 클래스(App 전반)

    private final UserDetailService userDetailService;

    @Bean
    // 사용자 정보, 비밀번호 인코더 받아와서 인증 처리
    public AuthenticationProvider authenticationProvider() {
        // AuthenticationProvider 인터페이스를 구현한 DaoAuthenticationProvider 객체 생성
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    // 인증을 처리하는 인증 매니저
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    // 비밀번호 BCrypt 암호화를 위한 빈
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
}

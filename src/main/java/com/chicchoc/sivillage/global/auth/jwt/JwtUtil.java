package com.chicchoc.sivillage.global.auth.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class JwtUtil {

    // 현재 인증된 사용자의 UUID를 가져오는 메서드
    public static String getUserUuid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();  // UUID 반환
        }

        throw new IllegalStateException("현재 인증된 사용자가 없습니다.");
    }


}
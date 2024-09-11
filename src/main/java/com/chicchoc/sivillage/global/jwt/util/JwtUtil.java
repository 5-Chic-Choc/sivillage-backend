package com.chicchoc.sivillage.global.jwt.util;

import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
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

        log.error("JwtUtil.getUserUuid() -> 인증되지 않은 사용자입니다.");
        throw new BaseException(BaseResponseStatus.NO_SIGN_IN);
    }
}
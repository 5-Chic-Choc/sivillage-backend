package com.chicchoc.sivillage.global.common.aop;

import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AuthenticationCheckAspect {

    // @CheckAuthentication 어노테이션이 붙은 메서드에 대해서만 로그인 체크를 수행
    @Before("@annotation(com.chicchoc.sivillage.global.common.aop.annotation.CheckAuthentication)"
            + " && args(.., authentication)")
    public void checkAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException(BaseResponseStatus.NO_SIGN_IN.getMessage());
        }
    }
}

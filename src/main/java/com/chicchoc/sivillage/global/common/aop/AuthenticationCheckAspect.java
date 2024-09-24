package com.chicchoc.sivillage.global.common.aop;

import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AuthenticationCheckAspect {

    // @CheckAuthentication 어노테이션이 붙은 메서드에 대해서만 로그인 체크를 수행하고,
    // //로그인이 되어 있지 않다면 BaseException을 발생시킨다.
    @Before("@annotation(com.chicchoc.sivillage.global.common.aop.annotation.CheckAuthentication)"
            + " && args(.., authentication)")
    public void checkAuthentication(Authentication authentication) {
        if (authentication == null) {
            log.error("인증이 되어있지 않습니다.");
            throw new BaseException(BaseResponseStatus.NO_SIGN_IN);
        }
    }
}

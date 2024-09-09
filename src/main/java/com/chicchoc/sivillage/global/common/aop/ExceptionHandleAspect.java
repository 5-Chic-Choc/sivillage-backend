package com.chicchoc.sivillage.global.common.aop;

import com.chicchoc.sivillage.global.auth.exception.UnknownException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionHandleAspect {
    // 알 수 없이 발생하는 예외에 대해 처리하는 Aspect

    @Pointcut("@annotation(com.chicchoc.sivillage.global.common.aop.annotation.ExceptionHandleAop)")
    public void exceptionHandledMethods() {
    }

    @Around("exceptionHandledMethods()")
    public Object handleUnknownException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (DataAccessException e) {
            log.error("UnknownException 발생: {}", e.getMessage());
            throw new UnknownException("데이터베이스 오류가 발생했습니다. 관리자에게 문의하세요.");
        }
    }
}

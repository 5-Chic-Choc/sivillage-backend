package com.chicchoc.sivillage.global.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAop {

    // Slf4j Logger 선언
    private static final Logger logger = LoggerFactory.getLogger(LoggingAop.class);

    @Pointcut("@annotation(com.chicchoc.sivillage.global.common.aop.annotation.MethodLogger)")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Signature signature = proceedingJoinPoint.getSignature();
        CodeSignature codeSignature = (CodeSignature) signature;

        String className = codeSignature.getDeclaringTypeName();
        String methodName = codeSignature.getName();

        String[] argNames = codeSignature.getParameterNames();
        Object[] args = proceedingJoinPoint.getArgs();

        // 로그로 출력 (DEBUG 레벨)
        if (logger.isDebugEnabled()) {
            logger.debug("========================================");
            logger.debug("클래스명: {}, 메소드명: {}", className, methodName);

            for (int i = 0; i < argNames.length; i++) {
                logger.debug("{}: {}", argNames[i], args[i]); // 파라미터와 값 출력
            }
            logger.debug("========================================");
        }

        return proceedingJoinPoint.proceed();

    }
}

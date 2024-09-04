package com.chicchoc.sivillage.global.common.aop;

import com.chicchoc.sivillage.global.auth.exception.ValidException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

@Slf4j
@Aspect
@Component
public class ValidAop {

    //@ValidAop 어노테이션이 붙은 메소드를 대상으로 함을 정의
    @Pointcut("@annotation(com.chicchoc.sivillage.global.common.aop.annotation.ValidAop)")
    private void pointCut() {
    }

    //대상 메소드 실행 전, 후에 실행하며, 인자인 ProceedingJoinPoint로 대상 메소드를 실행
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //대상 메소드의 파라미터를 가져고, BeanPropertyBindingResult를 초기화
        Object[] args = proceedingJoinPoint.getArgs();
        BeanPropertyBindingResult bindingResult = null;

        //args 순회하며 BeanPropertyBindingResult(유효성 검사 결과)를 찾음
        for (Object arg : args) {
            if (arg.getClass() == BeanPropertyBindingResult.class) {
                bindingResult = (BeanPropertyBindingResult) arg;
                break;
            }
        }

        //예외가 없을 경우 대상 메소드 실행
        if (bindingResult == null) {
            return proceedingJoinPoint.proceed();
        }

        //유효성 검사 결과가 에러가 있을 경우, 에러를 Map에 담아 예외처리
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            });

            //유효성 검사 오류를 처리하는 ValidException을 발생시킴 => ControllerAdvice에서 처리 => 응답 반환
            throw new ValidException("유효성 검사 오류", errorMap);
        }

        Object target = proceedingJoinPoint.proceed();
        return target;
    }
}

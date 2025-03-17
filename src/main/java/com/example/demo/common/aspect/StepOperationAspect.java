package com.example.demo.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StepOperationAspect {

    @Pointcut("@annotation(com.example.demo.common.annotation.StepOperation)")
    public void stepOperation() {}

    @Before("stepOperation()")
    public void before() {
        System.out.println("Before Step Operation");
    }

    @After("stepOperation()")
    public void after() {
        System.out.println("After Step Operation");
    }

    @AfterReturning("stepOperation()")
    public void afterReturning() {
        System.out.println("After Returning Step Operation");
    }

    @AfterThrowing("stepOperation()")
    public void afterThrowing(JoinPoint joinPoint) {
        System.out.println("After Throwing Step Operation");
        System.out.println(joinPoint.getSignature().getName());
    }

    @Around("stepOperation()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around Step Operation");
        return joinPoint.proceed();
    }


}

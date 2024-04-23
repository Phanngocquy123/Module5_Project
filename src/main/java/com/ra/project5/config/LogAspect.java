package com.ra.project5.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    private final static Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("within(com.ra.project5.service.impl.*)")
    public void logForService(){};

    @Around("logForService()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
        LOGGER.info("[START] {}.{} with params {}", joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(), Arrays.asList(joinPoint.getArgs()));
        try {
            Long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            Long totalTimes = System.currentTimeMillis() - start;
            LOGGER.info("[END] {}.{} with return {}", joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName(), result);

            LOGGER.info("[LOG_ASPECT] {}.{} executed in {}ms", joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName(), totalTimes);
            return result;
        }catch (Exception ex){
            LOGGER.info("[EXCEPTION] {}.{} exception {}", joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName(), ex);
            throw ex;
        }
    }
}

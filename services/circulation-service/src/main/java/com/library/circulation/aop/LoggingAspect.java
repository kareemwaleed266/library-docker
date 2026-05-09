package com.library.circulation.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.library.circulation.service.impl..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        long startTime = System.currentTimeMillis();

        log.debug("Starting {}.{}", className, methodName);

        try {
            Object result = joinPoint.proceed();

            long duration = System.currentTimeMillis() - startTime;
            log.debug("Finished {}.{} in {} ms", className, methodName, duration);

            return result;
        } catch (Throwable exception) {
            long duration = System.currentTimeMillis() - startTime;
            log.error(
                    "Failed {}.{} after {} ms with error: {}",
                    className,
                    methodName,
                    duration,
                    exception.getMessage()
            );

            throw exception;
        }
    }
}

package com.henriquenascimento.demo.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Aspect
@Log4j2
@Component
public class ControllerLoggingAspect {

    @Before("execution(* com.henriquenascimento.demo.controller..*(..))")
    public void logMethodCall(final JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Parameter[] parameters = method.getParameters();
        String requestMethod = getRequestMethod(method);

        StringBuilder logMessage = new StringBuilder("Controller: " + className + ", Method: " + methodName + " (" + requestMethod + ") with parameters: ");
        for (int i = 0; i < parameters.length; i++) {
            logMessage.append(parameters[i].getName()).append("=").append(args[i]).append(", ");
        }

        log.info(logMessage.substring(0, logMessage.length() - 2)); // Remove the last comma and space
    }

    @AfterReturning(pointcut = "execution(* com.henriquenascimento.demo.controller..*(..))", returning = "result")
    public void logMethodReturn(final JoinPoint joinPoint, final Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        log.info("Controller: {}, Method: {} returned: {}", className, methodName, result);
    }

    private String getRequestMethod(Method method) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            return "GET";
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            return "POST";
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            return "PUT";
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            return "DELETE";
        } else if (method.isAnnotationPresent(PatchMapping.class)) {
            return "PATCH";
        } else if (method.isAnnotationPresent(RequestMapping.class)) {
            final RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            if (Arrays.asList(requestMapping.method()).contains(RequestMethod.OPTIONS)) {
                return "OPTIONS";
            } else if (Arrays.asList(requestMapping.method()).contains(RequestMethod.HEAD)) {
                return "HEAD";
            }
            return String.join(", ", Arrays.toString(requestMapping.method()));
        }
        return "UNKNOWN";
    }

    // TODO save in MongoDB (id, controller, method, request, response, elapsedTime, createdAt)
}
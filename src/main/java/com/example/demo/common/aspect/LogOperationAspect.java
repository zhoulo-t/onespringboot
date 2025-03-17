package com.example.demo.common.aspect;

import com.example.demo.common.annotation.LogOperation;
import com.example.demo.entity.OperationLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class LogOperationAspect {
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("@annotation(com.example.demo.common.annotation.LogOperation)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        
        // 执行方法
        Object result = point.proceed();
        
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        
        // 保存日志
        saveLog(point, time);
        
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationLog operationLog = new OperationLog();
        
        // 获取注解上的描述
        LogOperation logOperation = method.getAnnotation(LogOperation.class);
        if (logOperation != null) {
            operationLog.setOperation(logOperation.value());
            operationLog.setOperationType(logOperation.type());
        }

        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        operationLog.setRequestMethod(className + "." + methodName + "()");

        // 请求的参数
        if (logOperation != null && logOperation.saveRequestData()) {
            Object[] args = joinPoint.getArgs();
            String params = objectMapper.writeValueAsString(args);
            operationLog.setRequestParams(params);
        }

        // 获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        
        // 设置IP地址
        operationLog.setIp(getIpAddr(request));
        
        // 设置执行时长
        operationLog.setTime(time);
        
        // 设置创建时间
        operationLog.setCreateTime(new Date());

        // TODO: 这里可以将日志保存到数据库
        System.out.println("Operation Log: " + objectMapper.writeValueAsString(operationLog));
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
} 
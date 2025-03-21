package com.example.demo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 在Controller方法执行之前调用
        logger.info("Interceptor preHandle: {} {}", request.getMethod(), request.getRequestURI());
        return true; // 返回true表示继续执行，false表示中断执行
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
                          org.springframework.web.servlet.ModelAndView modelAndView) {
        // 在Controller方法执行之后，视图渲染之前调用
        logger.info("Interceptor postHandle: {} {}", request.getMethod(), request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                              Object handler, Exception ex) {
        // 在整个请求完成之后调用，即DispatcherServlet渲染了视图之后
        logger.info("Interceptor afterCompletion: {} {}", request.getMethod(), request.getRequestURI());
        if (ex != null) {
            logger.error("Request failed with exception", ex);
        }
    }
} 
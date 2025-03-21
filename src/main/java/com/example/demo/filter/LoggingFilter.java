package com.example.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 记录请求开始时间
        Instant start = Instant.now();
        
        // 记录请求信息
        logger.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        logger.info("Client IP: {}", request.getRemoteAddr());
        
        // 继续处理请求
        filterChain.doFilter(request, response);
        
        // 计算处理时间
        Duration duration = Duration.between(start, Instant.now());
        
        // 记录响应信息
        logger.info("Response Status: {}", response.getStatus());
        logger.info("Processing Time: {}ms", duration.toMillis());
    }
} 
package com.example.demo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求头中的token
        String token = request.getHeader("Authorization");
        
        // 检查是否是登录请求，如果是则放行
        if (request.getRequestURI().contains("/api/auth/login")) {
            return true;
        }

        // 验证token
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // TODO: 验证token的有效性
        
        return true;
    }
} 
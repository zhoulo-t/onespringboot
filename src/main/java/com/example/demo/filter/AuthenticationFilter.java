package com.example.demo.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的token
        String token = request.getHeader("Authorization");
        
        // 检查是否是登录请求，如果是则放行
        if (request.getRequestURI().contains("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 验证token
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // TODO: 验证token的有效性
        
        // 验证通过，继续处理请求
        filterChain.doFilter(request, response);
    }
} 
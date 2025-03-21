package com.example.demo.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CharacterEncodingFilter extends OncePerRequestFilter {

    private static final String ENCODING = "UTF-8";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 设置请求编码
        request.setCharacterEncoding(ENCODING);

        // 设置响应编码
        response.setCharacterEncoding(ENCODING);
        response.setContentType("application/json;charset=" + ENCODING);

        // 继续处理请求
        filterChain.doFilter(request, response);
    }
} 
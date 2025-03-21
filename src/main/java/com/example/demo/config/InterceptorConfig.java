package com.example.demo.config;

import com.example.demo.interceptor.AuthenticationInterceptor;
import com.example.demo.interceptor.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册日志拦截器
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns("/static/**"); // 排除静态资源

        // 注册认证拦截器
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/api/**")  // 拦截所有API请求
                .excludePathPatterns("/api/auth/login"); // 排除登录接口
    }
} 
package com.example.demo.common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {
    /**
     * 操作描述
     */
    String value() default "";

    /**
     * 操作类型
     */
    String type() default "";

    /**
     * 是否保存请求参数
     */
    boolean saveRequestData() default true;
} 
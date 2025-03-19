package com.example.demo.common.utils;

import lombok.Data;

@Data
public class Result<T> {

    private String code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>("200", "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>("200", "success", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>("200", message, data);
    }

    public static <T> Result<T> success(String code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(code, message, null);
    }


}

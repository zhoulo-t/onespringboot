package com.example.demo.controller;

import com.example.demo.common.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public R hello() {
        return R.ok("Hello, Spring Boot!");
    }
} 
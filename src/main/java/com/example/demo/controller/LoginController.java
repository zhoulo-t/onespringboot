package com.example.demo.controller;

import com.example.demo.common.utils.Result;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = loginService.login(loginRequest);
        return Result.success(response);
    }

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        User registeredUser = loginService.register(user);
        return Result.success(registeredUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // 这里可以添加登出逻辑，比如清除token等
        return ResponseEntity.ok("Logged out successfully");
    }
} 
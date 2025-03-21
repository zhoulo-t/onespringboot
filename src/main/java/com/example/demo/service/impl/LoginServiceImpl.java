package com.example.demo.service.impl;

import com.example.demo.dao.UserMapper;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userMapper.selectById(3L);
        LoginResponse response = new LoginResponse();
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }

    @Override
    public User register(User user) {
        return new User();
    }

}
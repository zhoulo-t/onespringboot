package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.vo.UserVO;

public interface UserService {
    UserVO createUser(UserDTO userDTO);
    UserVO getUserById(Long id);
    UserVO updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
} 
package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        // 准备测试数据
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setPassword("123456");
        userDTO.setEmail("test@example.com");
        userDTO.setPhone("13800138000");

        // 执行测试
        UserVO userVO = userService.createUser(userDTO);

        // 验证结果
        assertNotNull(userVO);
        assertNotNull(userVO.getId());
        assertEquals(userDTO.getUsername(), userVO.getUsername());
        assertEquals(userDTO.getEmail(), userVO.getEmail());
        assertEquals(userDTO.getPhone(), userVO.getPhone());
        assertEquals(1, userVO.getStatus()); // 验证默认状态为1

        // 验证缓存
        UserVO cachedUser = userService.getUserById(userVO.getId());
        assertNotNull(cachedUser);
        assertEquals(userVO.getId(), cachedUser.getId());
        assertEquals(userVO.getUsername(), cachedUser.getUsername());
    }

    @Test
    public void testCreateUserWithInvalidData() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("zhangsan");
        userDTO.setPassword("123456");
        userDTO.setEmail("test@example.com");
        userDTO.setPhone("13800138000");
        userService.createUser(userDTO);
    }
} 
package com.example.demo.controller;

import com.example.demo.common.annotation.LogOperation;
import com.example.demo.common.utils.R;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @LogOperation(value = "创建用户", type = "CREATE")
    @PostMapping
    public R create(@RequestBody UserDTO user) {
        return R.ok(userService.createUser(user));
    }

    @LogOperation(value = "获取用户信息", type = "QUERY")
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        return R.ok(userService.getUserById(id));
    }

    @LogOperation(value = "更新用户信息", type = "UPDATE")
    @PutMapping("/{id}")
    public R update(@PathVariable Long id, @RequestBody UserDTO user) {
        return R.ok(userService.updateUser(id, user));
    }

    @LogOperation(value = "删除用户", type = "DELETE")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return R.ok();
    }
} 
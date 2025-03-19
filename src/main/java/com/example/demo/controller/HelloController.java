package com.example.demo.controller;

import com.example.demo.common.utils.R;
import com.example.demo.common.utils.Result;
import com.example.demo.vo.UserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public R hello() {
        return R.ok("Hello, Spring Boot!");
    }

    @GetMapping("/testRestult1")
    public Result<?> testResult1() {
        return Result.success();
    }

    @GetMapping("/testRestult2")
    public Result<UserVO> testResult2() {
        UserVO userVO = new UserVO();
        return Result.success(userVO);
    }

} 
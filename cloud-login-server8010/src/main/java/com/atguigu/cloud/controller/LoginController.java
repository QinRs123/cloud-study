package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.User;
import com.atguigu.cloud.response.Result;
import com.atguigu.cloud.response.ResultCodeEnum;
import com.atguigu.cloud.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class LoginController {

    @Resource
    private UserService userService;

    @GetMapping("/user/getAll")
    public Result getAll(){
        List<User> list=userService.getAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/user/login")
    public Result login(@RequestParam String username,@RequestParam("password") String password){
        return userService.login(username,password);
    }
}

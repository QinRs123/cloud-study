package com.atguigu.cloud.controller;

import com.atguigu.cloud.response.Result;
import com.atguigu.cloud.response.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
//@RequestMapping("/test")
public class TestController {

    @Value("${server.port}")
    private String port;
    @GetMapping("/test/get")
    public String test(){
        return "hello word ..."+port;
    }

    @GetMapping("/test/result")
    public Result<String> result(){
        return  Result.build("yes+"+port, ResultCodeEnum.SUCCESS);
    }


}

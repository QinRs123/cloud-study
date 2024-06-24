package com.atguigu.cloud.controller;

import com.atguigu.cloud.api.TestFeignApis;
import com.atguigu.cloud.api.TestGatewayApis;
import com.atguigu.cloud.response.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

//    @Resource
//    private TestFeignApis testFeignApis;

    @Resource
    private TestGatewayApis testGatewayApis;

    @GetMapping("/test")
    public String test(){
//       return testFeignApis.test();
        return testGatewayApis.test();
    }

    @GetMapping("/result")
    public Result<String> result(){
//       return testFeignApis.test();
        return testGatewayApis.result();
    }
}

package com.atguigu.cloud.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="cloud-test-server")
public interface TestFeignApis {
    @GetMapping("/test/get")
    public String test();
}

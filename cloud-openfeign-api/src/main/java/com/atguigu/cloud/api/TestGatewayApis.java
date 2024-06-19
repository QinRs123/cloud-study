package com.atguigu.cloud.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="cloud-gateway")
public interface TestGatewayApis {
    @GetMapping("/test/get")
    public String test();
}

package com.atguigu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.atguigu.cloud.mapper")
@SpringBootApplication
public class Main8010 {
    public static void main(String[] args) {
        SpringApplication.run(Main8010.class,args);
    }
}

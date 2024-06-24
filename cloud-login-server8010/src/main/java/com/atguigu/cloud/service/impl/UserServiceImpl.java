package com.atguigu.cloud.service.impl;

import com.alibaba.fastjson2.JSON;
import com.atguigu.cloud.entities.User;
import com.atguigu.cloud.mapper.UserMapper;
import com.atguigu.cloud.response.Result;
import com.atguigu.cloud.response.ResultCodeEnum;
import com.atguigu.cloud.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public Result login(String username, String password) {
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        /**
         *
         * 设置Hash类型存储时，对象序列化报错解决
         */
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());


        User user=userMapper.login(username,password);
        if(user==null){
            return Result.build("账户不存在~", ResultCodeEnum.LOGIN_ERROR);
        }
        System.out.println(user);
        //生成token
        String token = UUID.randomUUID().toString().replace("-", "");
        //记录到redis中
        redisTemplate.opsForValue().set("user:login:" + token , JSON.toJSONString(user) , 30 , TimeUnit.MINUTES);
        return Result.build(token,ResultCodeEnum.SUCCESS);
    }
}

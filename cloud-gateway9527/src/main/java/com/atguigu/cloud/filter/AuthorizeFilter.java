package com.atguigu.cloud.filter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson2.JSON;
import com.atguigu.cloud.entities.User;
import com.atguigu.cloud.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * 网关token 过滤器验证
 */
//@Component
@Slf4j
public class AuthorizeFilter implements Ordered, GlobalFilter {
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取request response 对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //是否是登录
        String path = request.getURI().getPath();
        if(path.contains("/login")){
            //放行
            return chain.filter(exchange);
        }
        //获取token
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());


        String token = request.getHeaders().getFirst("token");

        //判断token是为空
        if(StringUtils.isBlank(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //判断token 是否有效
        //redisTemplate.opsForValue().set("token:"+token,
        // JSON.toJSONString(user) ,30 , TimeUnit.MINUTES);
        String userString = redisTemplate.opsForValue().get("user:login:" + token);

        String sysUserInfoJson = redisTemplate.opsForValue().get("user:login:" + token);
        if(StrUtil.isEmpty(sysUserInfoJson)) {
            log.info("token is error");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 将用户数据存储到ThreadLocal中
        User sysUser = JSON.parseObject(sysUserInfoJson, User.class);
        AuthContextUtil.set(sysUser);

        // 重置Redis中的用户数据的有效时间
        redisTemplate.expire("user:login:" + token , 30 , TimeUnit.MINUTES) ;
        return chain.filter(exchange);
    }

    /**
     * 优先级设置，值越小 优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}

package com.atguigu.cloud.service;

import com.atguigu.cloud.entities.User;
import com.atguigu.cloud.response.Result;

import java.util.List;

public interface UserService {
    List<User> getAll();

    Result login(java.lang.String username, java.lang.String password);
}

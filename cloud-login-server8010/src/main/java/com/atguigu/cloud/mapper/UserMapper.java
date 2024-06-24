package com.atguigu.cloud.mapper;

import com.atguigu.cloud.entities.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


//@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {

    @Select("select * from user")
    List<User> getAll();

    User login(String username, String password);
}

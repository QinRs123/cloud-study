package com.atguigu.cloud.utils;


import com.atguigu.cloud.entities.User;

public class AuthContextUtil {

    // 创建一个ThreadLocal对象
    private static final ThreadLocal<User> threadLocal = new ThreadLocal<>() ;

    // 定义存储数据的静态方法
    public static void set(User user) {
        threadLocal.set(user);
    }

    // 定义获取数据的方法
    public static User get() {
        return threadLocal.get() ;
    }

    // 删除数据的方法
    public static void remove() {
        threadLocal.remove();
    }
}

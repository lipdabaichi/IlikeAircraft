package com.jt.util;

import com.jt.pojo.User;

//该对象保存的是用户信息
public class UserThreadLocal {

    //JVM直接创建的
    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void set(User user) {
        threadLocal.set(user);
    }
    public static User get(){
        return threadLocal.get();
    }
    //使用threadLocal时,必须注意内存的泄漏!!!
    public static void remove(){
        threadLocal.remove();
    }

}

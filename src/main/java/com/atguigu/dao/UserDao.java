package com.atguigu.dao;

import com.atguigu.bean.User;

//接口可以用于项目的解耦
public interface UserDao {
    User findByUsername(String username) throws Exception;

    void addUser(User user) throws Exception;

    User findByEmail(String email)throws Exception;
}

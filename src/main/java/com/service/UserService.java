package com.service;

import com.bean.User;

public interface UserService {
    //处理注册业务
    void doRegister(User user) throws Exception;

    User doLogin(User user) throws Exception;

    void findByUsername(String username) throws Exception;

    void findByEmail(String email) throws Exception;
}

package com.service.impl;

import com.bean.User;
import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.service.UserService;
import com.utils.MD5Util;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void doRegister(User user) throws Exception {
        //注册之前校验用户名是否已存在，调用持久层的方法,查找到了就表示已存在
        User byUsername = userDao.findByUsername(user.getUsername());
        if (byUsername != null) {
            //当前用户已存在
            throw new RuntimeException("注册失败，用户名已存在");
        }
        //需求：注册时要对用户密码进行加密
        String userPwd = user.getUserPwd();
        String encode = MD5Util.encode(userPwd);//将明文进行加密
        //将加密后的密码设置到user中
        user.setUserPwd(encode);

        //处理注册，就是调用持久层的方法添加用户
        userDao.addUser(user);
    }

    //处理登录的业务
    @Override
    public User doLogin(User user) throws Exception {
        //根据用户名查询用户信息
        User byUsername = userDao.findByUsername(user.getUsername());
        if (byUsername != null) {
            //用户名正确，那么校验密码
            //user中的密码是用户输入的密码，对其进行MD5加密之后和数据库进行比对
            String encode = MD5Util.encode(user.getUserPwd());
            if (encode.equals(byUsername.getUserPwd())) {
                //密码正确，登录成功
                return byUsername;
            } else {
                throw new RuntimeException("密码错误，登录失败");
            }
        }
        throw new RuntimeException("用户名错误，登录失败");
    }

    //根据username查询user
    @Override
    public void findByUsername(String username) throws Exception {
        if (userDao.findByUsername(username) != null) {
            //用户名已存在
            throw new RuntimeException("用户名已存在");
        }
    }

    @Override
    public void findByEmail(String email) throws Exception{
        if (userDao.findByEmail(email)!=null){
            //邮箱已存在
            throw new RuntimeException("邮箱已被使用");
        }
    }

}

package com.dao.impl;

import com.bean.User;
import com.dao.BaseDao;
import com.dao.UserDao;
//经过二次封装，该类不直接依赖dbutils，便于后期维护


/**
 * 用户模块的持久层类
 */
public class UserDaoImpl extends BaseDao<User> implements UserDao {

    /**
     * 根据用户名查找用户
     */
    @Override
    public User findByUsername(String username) throws Exception {
        String sql = "select user_id as userId,user_name as username,user_pwd as userPwd,email from t_user where user_name=?";
        //对查询的字段取别名来使之与User中的属性名称相同
        //获取连接
        return getBean(User.class, sql, username);
    }

    /**
     * 添加用户
     *
     * @param user 要添加的用户对象
     */
    @Override
    public void addUser(User user) throws Exception {
        String sql = "insert into t_user (user_name,user_pwd,email) values (?,?,?)";
        update(sql, user.getUsername(), user.getUserPwd(), user.getEmail());
    }

    @Override
    public User findByEmail(String email) throws Exception{
        String sql = "select user_id as userId,user_name as username,user_pwd as userPwd,email from t_user where email=?";
        return getBean(User.class, sql, email);
    }
}

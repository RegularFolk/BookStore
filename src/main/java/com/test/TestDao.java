package com.test;

import com.bean.User;
import com.dao.impl.UserDaoImpl;
import org.junit.Test;

public class TestDao {
    @Test
    public void testAddUser() throws Exception {
        User jay = new User(null, "aobama", "654321", "654321@qq.com");
        new UserDaoImpl().addUser(jay);
    }

    @Test
    public void testFindUser() throws Exception {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        User jay = null;
        try {
            jay = userDaoImpl.findByUsername("aobama");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jay);
    }
}

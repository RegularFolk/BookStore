package com.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 提供三个方法
 * 1.获取连接池对象
 * 2.从连接池中获取连接
 * 3.将连接归还到连接池
 */
public class JDBCUtil {

    private static DataSource dataSource;
    private static final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    static {//静态代码块在类加载时执行，且仅执行一次
        try {
            //1.使用类加载器读取配置文件，转成字节输入流
            InputStream resourceAsStream = JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            //2.使用properties对象读取流
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            //3.使用DruidDataSourceFactory创建连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 获取连接
     * 使用ThreadLocal,使同一线程中拿到的是同一个connection,实现事务回滚的功能
     * 使用ThreadLocal绑定一个连接到主线程
     */
    public static Connection getConnection() {
        try {
            Connection connection = connectionThreadLocal.get();
            if (connection == null) {
                //threadLocal中暂时还没有连接,从连接池中获取连接
                connection = dataSource.getConnection();
                //将这个连接存储到ThreadLocal
                connectionThreadLocal.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 全程保持仅有一个连接被使用，在没有框架的情况下实现事务的回滚
     */
    public static void releaseConnection() {
        try {
            //将连接归还给连接池
            getConnection().close();
            //移除ThreadLocal中的连接
            connectionThreadLocal.remove();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


}

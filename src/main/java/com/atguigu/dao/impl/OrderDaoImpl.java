package com.atguigu.dao.impl;

import com.atguigu.bean.Book;
import com.atguigu.bean.Order;
import com.atguigu.bean.OrderItem;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {
    @Override
    public void insertOrder(Order order) throws Exception {
        @SuppressWarnings("SqlResolve") String sql = "insert into t_order (order_sequence,create_time,total_count,total_amount,order_status,user_id) values(?,?,?,?,?,?)";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            //添加订单信息,使用JDBC获取自增长主键
            //预编译

            //设置参数
            preparedStatement.setObject(1, order.getOrderSequence());
            preparedStatement.setObject(2, order.getCreateTime());
            preparedStatement.setObject(3, order.getTotalCount());
            preparedStatement.setObject(4, order.getTotalAmount());
            preparedStatement.setObject(5, order.getOrderStatus());
            preparedStatement.setObject(6, order.getUserId());

            //执行sql语句
            preparedStatement.executeUpdate();

            //获取自增长主键值
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                final int orderId = generatedKeys.getInt(1);
                //存入order对象
                order.setOrderId(orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }


    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "select order_id as orderId , order_sequence as orderSequence , create_time as createTime , total_count as totalCount , total_amount as totalAmount , order_status as orderStatus , user_id as userId from t_order";
        return getBeanList(Order.class, sql);
    }

    @Override
    public List<Order> getOrdersByUserId(int id) {
        String sql = "select order_id as orderId , order_sequence as orderSequence , create_time as createTime , total_count as totalCount , total_amount as totalAmount , order_status as orderStatus , user_id as userId from t_order where user_id = ?";
        return getBeanList(Order.class, sql, id);
    }


}

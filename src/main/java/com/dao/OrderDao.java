package com.dao;

import com.bean.Order;

import java.util.List;


public interface OrderDao {
    /**
     * 保存订单信息
     */
    void insertOrder(Order order) throws Exception;

    /**
     * 返回所有订单信息
     * */
    List<Order> getAllOrders();

    List<Order> getOrdersByUserId(int id);
}

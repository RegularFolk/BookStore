package com.atguigu.service;

import com.atguigu.bean.*;

import java.util.List;


public interface OrderService {
    /**
     * 结算业务
     */
    String checkOut(User user, Cart cart) throws Exception;

    List<Order> getAllOrders();

    List<OrderItem> getBooksByOrderId(int id);

    List<Order> getOrdersByUserId(int id);
}

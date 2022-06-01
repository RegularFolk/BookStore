package com.service;

import com.bean.Cart;
import com.bean.Order;
import com.bean.OrderItem;
import com.bean.User;

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

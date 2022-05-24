package com.atguigu.dao;


import com.atguigu.bean.Book;
import com.atguigu.bean.OrderItem;

import java.util.List;

public interface OrderItemDao {
    /**
     * 批量添加订单项
     */
    void insertOrderItemArr(Object[][] insertOrderItemArr)throws Exception;

    List<OrderItem> getBooksByOrderId(int id);
}

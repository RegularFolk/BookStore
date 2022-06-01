package com.dao;


import com.bean.OrderItem;

import java.util.List;

public interface OrderItemDao {
    /**
     * 批量添加订单项
     */
    void insertOrderItemArr(Object[][] insertOrderItemArr)throws Exception;

    List<OrderItem> getBooksByOrderId(int id);
}

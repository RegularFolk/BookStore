package com.atguigu.dao.impl;

import com.atguigu.bean.OrderItem;
import com.atguigu.dao.BaseDao;
import com.atguigu.dao.OrderItemDao;

import java.util.List;

public class OrderItemDaoImpl extends BaseDao<OrderItem> implements OrderItemDao {
    @Override
    public void insertOrderItemArr(Object[][] insertOrderItemArr) {
        String sql = "insert into t_order_item(book_name,price,img_path,item_count,item_amount,order_id) values (?,?,?,?,?,?)";
        batchUpdate(sql, insertOrderItemArr);
    }

    @Override
    public List<OrderItem> getBooksByOrderId(int id) {
        String sql = "select item_id as itemId , book_name as bookName , price , img_path as imgPath , item_count as itemCount , item_amount as itemAmount from t_order_item where order_id = ?";
        return getBeanList(OrderItem.class, sql, id);
    }


}

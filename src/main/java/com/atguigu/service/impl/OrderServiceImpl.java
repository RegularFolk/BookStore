package com.atguigu.service.impl;

import com.atguigu.bean.*;
import com.atguigu.constant.Constants;
import com.atguigu.dao.BookDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderItemDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.dao.impl.OrderDaoImpl;
import com.atguigu.dao.impl.OrderItemDaoImpl;
import com.atguigu.service.OrderService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderServiceImpl implements OrderService {
    final private OrderDao orderDao = new OrderDaoImpl();
    final private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    final private BookDao bookDao = new BookDaoImpl();

    @Override
    public String checkOut(User user, Cart cart) throws Exception {
        //往订单表中插入一条数据
        Order order = new Order();

        //生成一个唯一的订单号,使用UUID(JAVA提供的一个生成唯一字符串的工具类)或者拼接当前毫秒数
        String orderSequence = UUID.randomUUID().toString();
        order.setOrderSequence(orderSequence);

        //创建当前时间的字符串,存入到order中
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String createTime = dateFormat.format(new Date());
        order.setCreateTime(createTime);

        //获取订单中书的种类
        Collection<CartItem> bookValues = cart.getCartItemMap().values();
        int totalBookType = bookValues.size();

        //设置订单的书的总数
        order.setTotalCount(cart.getTotalCount());

        //设置订单的totalAmount
        order.setTotalAmount(cart.getTotalAmount());

        //设置订单的状态为未发货
        order.setOrderStatus(Constants.UNFILLED_ORDER);

        //设置订单的用户id
        order.setUserId(user.getUserId());

        //调用持久层方法保存order
        orderDao.insertOrder(order);

        //int bug = 1 / 0; 事务回滚测试

        //往订单详情表中插入多条订单详情,使用二维数组封装批处理需要的数据,一维表示要执行多少条数据(订单项的个数，就是购物车中购物项的个数)，二位表示每条数据有几个参数
        Object[][] insertOrderItemParamArr = new Object[totalBookType][6];
        ArrayList<CartItem> cartItemList = new ArrayList<>(bookValues);

        //批量修改书的库存和销量
        Object[][] updateBookParamArr = new Object[totalBookType][3];

        for (int i = 0; i < totalBookType; i++) {
            CartItem cartItem = cartItemList.get(i);
            //封装批量添加订单项的参数
            insertOrderItemParamArr[i][0] = cartItem.getBookName();
            insertOrderItemParamArr[i][1] = cartItem.getPrice();
            insertOrderItemParamArr[i][2] = cartItem.getImgPath();
            insertOrderItemParamArr[i][3] = cartItem.getCount();
            insertOrderItemParamArr[i][4] = cartItem.getAmount();
            insertOrderItemParamArr[i][5] = order.getOrderId();

            //封装批量修改t_book的库存和销量的参数
            updateBookParamArr[i][0] = cartItem.getCount();
            updateBookParamArr[i][1] = cartItem.getCount();
            updateBookParamArr[i][2] = cartItem.getBookId();
        }

        //调用持久层orderItemDao方法进行批量添加
        orderItemDao.insertOrderItemArr(insertOrderItemParamArr);

        //调用持久层方法批量修改
        bookDao.UpdateBookArr(updateBookParamArr);

        //返回订单的序列号
        return orderSequence;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    @Override
    public List<OrderItem> getBooksByOrderId(int id) {
        return orderItemDao.getBooksByOrderId(id);
    }

    @Override
    public List<Order> getOrdersByUserId(int id) {
        return orderDao.getOrdersByUserId(id);
    }
}

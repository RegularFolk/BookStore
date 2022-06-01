package com.servlet.model;

import com.bean.Order;
import com.bean.OrderItem;
import com.service.OrderService;
import com.service.impl.OrderServiceImpl;
import com.servlet.base.ModelBaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderManagerServlet extends ModelBaseServlet {

    private OrderService orderService = new OrderServiceImpl();

    /**
     * 查出所有订单，跳转到订单管理页面
     */
    public void toOrderManagerPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //查出所有订单
        List<Order> orderList;
        try {
            orderList = orderService.getAllOrders();
            request.setAttribute("orderList", orderList);
            processTemplate("manager/order_manager", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOrderItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<OrderItem> orderItemList;
        try {
            orderItemList = orderService.getBooksByOrderId(id);
            request.setAttribute("orderItemList", orderItemList);
            processTemplate("manager/order_item", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

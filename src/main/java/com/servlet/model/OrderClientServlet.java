package com.servlet.model;

import com.bean.Cart;
import com.bean.Order;
import com.bean.User;
import com.constant.Constants;
import com.service.OrderService;
import com.service.impl.OrderServiceImpl;
import com.servlet.base.ModelBaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderClientServlet extends ModelBaseServlet {
    final private OrderService orderService = new OrderServiceImpl();

    /**
     * 结算方法
     */
    public void checkOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute(Constants.CART_SESSION_KEY);
            User loginUser = (User) session.getAttribute(Constants.USER_SESSION_KEY);
            String orderSequence = orderService.checkOut(loginUser, cart);
            session.removeAttribute(Constants.CART_SESSION_KEY);
            request.setAttribute(Constants.ORDER_SEQUENCE, orderSequence);
        } catch (SQLException e) {
            e.printStackTrace();
            //抛出异常使filter接收到,需要使用到事务的情况下异常要往上抛,让filter接收到进行处理
            throw new RuntimeException(e.getMessage());
        }finally {
            processTemplate("cart/checkout", request, response);
        }
    }

    /**
     * 根据用户id查询对应订单
     * */
    public void getOrdersByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Order> orderList;
        try {
            orderList = orderService.getOrdersByUserId(id);
            request.setAttribute("orderList", orderList);
            processTemplate("order/order", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.servlet.model;

import com.bean.Book;
import com.bean.Cart;
import com.bean.CartItem;
import com.bean.CommonResult;
import com.constant.Constants;
import com.service.BookService;
import com.service.impl.BookServiceImpl;
import com.servlet.base.ModelBaseServlet;
import com.utils.JSONUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends ModelBaseServlet {
    private BookService bookService = new BookServiceImpl();

    /**
     * 添加商品到购物车
     */
    public void addCartItem(HttpServletRequest request, HttpServletResponse response) {
        CommonResult commonResult;
        try {
            //获取请求参数id的值
            Integer id = Integer.valueOf(request.getParameter("id"));
            Book bookById = bookService.getBookById(id);
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute(Constants.CART_SESSION_KEY);
            //判断是否已经添加过购物车
            if (cart == null) {
                //说明是第一次添加,就要新创建一个购物车对象
                cart = new Cart();
                cart.addBookToCart(bookById);
                //将cart存入session
                session.setAttribute(Constants.CART_SESSION_KEY, cart);
            } else {
                cart.addBookToCart(bookById);
                //不用再次在session里面添加，依旧是指向session中的对象
            }
            //添加购物车成功,获取购物车中的商品数量
            commonResult = CommonResult.ok().setResultData(cart.getTotalCount());
        } catch (Exception e) {
            e.printStackTrace();
            //添加购物车失败
            commonResult = CommonResult.error().setMessage("添加购物车失败");
        }
        JSONUtils.writeResult(response, commonResult);
    }

    /**
     * 跳转到显示购物车列表的页面
     */
    public void toCartPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processTemplate("cart/cart", request, response);
    }

    /**
     * 清空购物车
     */
    public void cleanCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //移除掉session中的cart
        request.getSession().removeAttribute(Constants.CART_SESSION_KEY);
        //跳转到购物车页面
        toCartPage(request, response);
    }

    /**
     * 获取更新后的totalCount和totalAmount
     */
    private Map<String, Object> updateCountAmount(Cart cart, Integer id) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("totalCount", cart.getTotalCount());
        responseMap.put("totalAmount", cart.getTotalAmount());
        CartItem cartItem = cart.getCartItemMap().get(id);
        if (cartItem != null) {
            responseMap.put("bookAmount", cartItem.getAmount());
        }
        return responseMap;
    }

    /**
     * 指定书的数量减一
     */
    public void countDecrease(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CommonResult commonResult;
        try {
            //获取到对应的书的id
            Integer id = Integer.valueOf(request.getParameter("id"));
            //从session中获取购物车信息
            Cart cart = (Cart) request.getSession().getAttribute(Constants.CART_SESSION_KEY);
            cart.itemCountDecrease(id);
            if (cart.getTotalCount() == 0) {
                request.getSession().removeAttribute(Constants.CART_SESSION_KEY);
            }
            commonResult = CommonResult.ok().setResultData(updateCountAmount(cart, id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage("添加失败");
        }
        JSONUtils.writeResult(response, commonResult);

    }

    /**
     * 指定书的数量加一
     */
    public void countIncrease(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CommonResult commonResult;
        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            Cart cart = (Cart) request.getSession().getAttribute(Constants.CART_SESSION_KEY);
            cart.itemCountIncrease(id);
            commonResult = CommonResult.ok().setResultData(updateCountAmount(cart, id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            commonResult = CommonResult.error();
        }
        JSONUtils.writeResult(response, commonResult);
    }

    /**
     * 删除指定书
     */
    public void removeItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CommonResult commonResult;
        try {
            Integer id = Integer.valueOf(request.getParameter("id"));
            Cart cart = (Cart) request.getSession().getAttribute(Constants.CART_SESSION_KEY);
            cart.removeCartItem(id);
            if (cart.getTotalCount() == 0) {
                request.getSession().removeAttribute(Constants.CART_SESSION_KEY);
            }
            commonResult = CommonResult.ok().setResultData(updateCountAmount(cart, id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            commonResult = CommonResult.error();
        }
        JSONUtils.writeResult(response, commonResult);
    }

    /**
     * 文本框修改
     */
    public void updateCartItemCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CommonResult commonResult;
        try {
            Integer newCount = Integer.valueOf(request.getParameter("newCount"));
            Integer id = Integer.valueOf(request.getParameter("id"));
            Cart cart = (Cart) request.getSession().getAttribute(Constants.CART_SESSION_KEY);
            cart.updateItemCount(id, newCount);
            commonResult = CommonResult.ok().setResultData(updateCountAmount(cart, id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            commonResult = CommonResult.error();
        }
        JSONUtils.writeResult(response, commonResult);
    }

    /**
     * 获取购物车的JSON信息
     */
    public void getCartJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> responseMap;
        CommonResult commonResult;
        try {
            //获取购物车信息
            final HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute(Constants.CART_SESSION_KEY);
            if (cart != null) {
                //相应给客户端的JSON {"totalCount":总条数,"totalAmount":总金额,"cartItemList":购物项的集合}
                responseMap = new HashMap<>();
                responseMap.put("totalCount", cart.getTotalCount());
                responseMap.put("totalAmount", cart.getTotalAmount());
                //获取购物项集合
                responseMap.put("cartItemList", new ArrayList<>(cart.getCartItemMap().values()));
                commonResult = CommonResult.ok().setResultData(responseMap);
            } else {
                commonResult = CommonResult.ok();
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage("查询购物车信息失败");
        }
        //将responseMap转换成JSON再传输给客户端
        JSONUtils.writeResult(response, commonResult);
    }

}

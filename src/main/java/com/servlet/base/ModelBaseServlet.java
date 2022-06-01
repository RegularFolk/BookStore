package com.servlet.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

//二次封装，可被其它Servlet调用
public class ModelBaseServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String method = request.getParameter("method");

        //利用反射优化
        Method declaredMethod;
        try {
            declaredMethod = this.getClass().getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            declaredMethod.setAccessible(true);//强行设置方法为public
            declaredMethod.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

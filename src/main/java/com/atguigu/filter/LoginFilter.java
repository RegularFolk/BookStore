package com.atguigu.filter;

import com.atguigu.bean.CommonResult;
import com.atguigu.bean.User;
import com.atguigu.constant.Constants;
import com.atguigu.utils.JSONUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //判断当前是否已登录
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        User loginUser = (User) session.getAttribute(Constants.USER_SESSION_KEY);
        if (loginUser == null) {
            //说明未登录,存储一个errorMessage,不能直接转发到页面，否则thymeleaf无法解析
            //ajax发送的异步请求无法使用一般的请求转发和重定向
            //响应数据给客户端,告诉客户端未登录
            JSONUtils.writeResult(httpServletResponse, CommonResult.error().setMessage("unlogin"));
            return;
        }
        //已登录则放行
        chain.doFilter(request, response);
    }
}

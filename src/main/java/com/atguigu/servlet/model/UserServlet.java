package com.atguigu.servlet.model;

import com.atguigu.bean.CommonResult;
import com.atguigu.bean.User;
import com.atguigu.constant.Constants;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;
import com.atguigu.servlet.base.ModelBaseServlet;
import com.atguigu.utils.JSONUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class UserServlet extends ModelBaseServlet {
    private UserService userService = new UserServiceImpl();

    /**
     * 跳转到登陆页面
     */
    public void toLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processTemplate("user/login", request, response);
    }

    /**
     * 处理登录校验
     */
    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //登录校验
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //将username和passWord封装到User对象
        User user = new User(null, username, password, null);

        //调用业务层方法，处理登录
        try {
            User loginUser = userService.doLogin(user);
            //没有出现异常，登录成功，那么跳转到登录成功页面,不再能使用重定向
            //将loginUser对象存储到到会话域对象
            HttpSession session = request.getSession();
            session.setAttribute(Constants.USER_SESSION_KEY, loginUser);
            processTemplate("user/login_success", request, response);
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常,响应失败信息,往域对象中存储登陆失败信息，再跳转到登陆页面显示
            request.setAttribute("errorMessage", e.getMessage());
            processTemplate("user/login", request, response);
        }
    }

    /**
     * 处理注册
     */
    public void toRegisterPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processTemplate("user/regist", request, response);
    }


    public void doRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取请求参数
            Map<String, String[]> parameterMap = request.getParameterMap();

            //获取用户输入的验证码
            String codes = parameterMap.get("code")[0];
            //从session获取服务器生成的验证码
            String checkCode = (String) request.getSession().getAttribute("checkCode");
            //校验验证码,忽略大小写
            if (checkCode.equalsIgnoreCase(codes)) {
                //封装到bean
                User user = new User();
                BeanUtils.populate(user, parameterMap);
                userService.doRegister(user);
                request.getSession().setAttribute(Constants.USER_SESSION_KEY, user);
                processTemplate("user/regist_success", request, response);
            } else {
                //验证码错误
                throw new RuntimeException("验证码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "注册失败" + e.getMessage());
            processTemplate("user/regist", request, response);
        }
    }

    /**
     * 退出登录
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //立即让本次会话失效
        request.getSession().invalidate();

        //跳转到首页
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    /**
     * 检查注册的用户名是否已存在
     */
    public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CommonResult commonResult;
        try {
            //获取username
            String username = request.getParameter(Constants.USERNAME);
            //调用业务层的方法,查询user
            userService.findByUsername(username);
            //表示用户名不存在
            commonResult = CommonResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            //表示用户名已存在
            commonResult = CommonResult.error().setMessage("用户名已存在,请重新设置用户名");
        }
        //将commonResult相应给客户端
        JSONUtils.writeResult(response, commonResult);
    }

    /**
     * 检查注册的邮箱是否已存在
     */
    public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CommonResult commonResult;
        try {
            //获取email
            final String email = request.getParameter(Constants.EMAIL);
            userService.findByEmail(email);
            //表示邮箱未使用
            commonResult = CommonResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage("邮箱已被注册,请重新设置邮箱");
        }
        JSONUtils.writeResult(response, commonResult);
    }

    /**
     * 检查验证码是否正确
     */
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CommonResult commonResult;
        try {
            final String code = request.getParameter("code");
            final String checkCode = (String) request.getSession().getAttribute("checkCode");
            //验证码正确
            if (checkCode.equalsIgnoreCase(code)) {
                commonResult = CommonResult.ok();
            } else {
                throw new RuntimeException("验证码错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonResult = CommonResult.error().setMessage("验证码错误,请重新输入");
        }
        JSONUtils.writeResult(response, commonResult);
    }
}

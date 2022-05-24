package com.atguigu.utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


public class JSONUtils {
    /**
     * 将JSON封装到指定类型的bean
     */
    public static Object parseJsonToBean(HttpServletRequest request, Class<?> tClass) {
        try (BufferedReader reader = request.getReader()) {
            //请求体的数据在这个reader里面
            StringBuilder stringBuilder = new StringBuilder();
            String string;
            while ((string = reader.readLine()) != null) {
                stringBuilder.append(string);
            }
            //stringBuilder里面的内容就是读取到的请求体的数据,一段JSON
            //将JSON封装到user类中(JSON解析)
            return new Gson().fromJson(stringBuilder.toString(), tClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 后端也可以将一个对象响应给前端,将对象转成JSON字符串,再响应给前端
     */
    public static void writeResult(HttpServletResponse response, Object o) {
        try {
            final Gson gson = new Gson();
            final String s = gson.toJson(o);
            //异步请求的response.getWriter().write()和同步请求的有所不同
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}

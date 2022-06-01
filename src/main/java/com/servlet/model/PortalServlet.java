package com.servlet.model;

import com.bean.Book;
import com.service.BookService;
import com.service.impl.BookServiceImpl;
import com.servlet.base.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PortalServlet extends ViewBaseServlet {
    private BookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //查询动态数据
            List<Book> bookList = bookService.getBookList();

            //将动态数据存储到请求域
            request.setAttribute("bookList", bookList);

            processTemplate("index", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

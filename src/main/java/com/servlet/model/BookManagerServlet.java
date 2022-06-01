package com.servlet.model;

import com.bean.Book;
import com.service.BookService;
import com.service.impl.BookServiceImpl;
import com.servlet.base.ModelBaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BookManagerServlet extends ModelBaseServlet {
    private BookService bookService = new BookServiceImpl();

    /**
     * 跳转到图书管理页面
     */
    public void toBookManagerPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //查询出图书列表
        List<Book> bookList;
        try {
            bookList = bookService.getBookList();
            request.setAttribute("bookList", bookList);

            //将图书列表存储到请求域用于展示
            processTemplate("manager/book_manager", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除指定图书
     */
    public void removeBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取要删除的图书的id
        Integer id = Integer.valueOf(request.getParameter("id"));

        //调用业务层方法
        try {
            bookService.removeBook(id);
            //删除成功，重新查询所有图书信息(重定向)
            response.sendRedirect(request.getContextPath() + "/bookManager?method=toBookManagerPage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到添加图书页面
     */
    public void toAddPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processTemplate("manager/book_edit", request, response);
    }

    /**
     * 添加或者修改图书
     */
    public void saveOrUpdateBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //封装
        try {
            Book book = new Book();
            BeanUtils.populate(book, parameterMap);

            //判断修改还是添加
            if (book.getBookId() != null) {
                //修改
                bookService.editBook(book);
            } else {
                //添加
                //设置一个固定的imgPath
                book.setImgPath("static/uploads/xiaowangzi.jpg");

                //调用业务层方法
                bookService.saveBook(book);

            }
            //保存成功则重新查询所有图书
            response.sendRedirect(request.getContextPath() + "/bookManager?method=toBookManagerPage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到修改页面，需要做数据回显，根据id查询
     */
    public void toEditPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取客户端传入的id
        Integer id = Integer.valueOf(request.getParameter("id"));

        try {
            Book bookById = bookService.getBookById(id);
            request.setAttribute("book", bookById);
            processTemplate("manager/book_edit", request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

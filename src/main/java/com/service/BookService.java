package com.service;

import com.bean.Book;

import java.util.List;

public interface BookService {
    /**
     * 查询所有图书列表
     */
    List<Book> getBookList() throws Exception;

    /**
     * 删除图书
     */
    void removeBook(Integer bookId) throws Exception;

    /**
     * 添加图书
     */
    void saveBook(Book book) throws Exception;

    /**
     * 根据id获取book
     */
    Book getBookById(Integer id) throws Exception;

    /**
     * 修改信息
     */
    void editBook(Book book)throws Exception;
}

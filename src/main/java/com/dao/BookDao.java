package com.dao;

import com.bean.Book;

import java.util.List;

public interface BookDao {
    /**
     * 查询所有图书列表
     */
    List<Book> selectBookList() throws Exception;

    /**
     * 根据id删除图书
     */
    void deleteBook(Integer BookId) throws Exception;

    /**
     * 添加图书
     */
    void insertBook(Book book) throws Exception;

    /**
     * 根据id查询图书
     */
    Book selectBookByPrimaryKey(Integer id) throws Exception;

    /**
     * 修改图书信息
     */
    void updateBook(Book book) throws Exception;

    /**
     * 批量修改书的库存和销量
     */
    void UpdateBookArr(Object[][]updateBookParamArr) throws Exception;
}

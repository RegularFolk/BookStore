package com.dao.impl;

import com.bean.Book;
import com.dao.BaseDao;
import com.dao.BookDao;

import java.util.List;

public class BookDaoImpl extends BaseDao<Book> implements BookDao {
    @Override
    public List<Book> selectBookList() throws Exception {
        String sql = "select book_id as bookId , book_name as bookName,author,price , sales , stock , img_path as imgPath from t_book";
        return getBeanList(Book.class, sql);
    }

    @Override
    public void deleteBook(Integer bookId) throws Exception {
        String sql = "delete from t_book where book_id = ?";
        update(sql, bookId);
    }

    @Override
    public void insertBook(Book book) throws Exception {
        String sql = "insert into t_book (book_name,author,price,sales,stock,img_path) values(?,?,?,?,?,?)";
        update(sql, book.getBookName(), book.getAuthor(), book.getPrice(), book.getSales(), book.getStock(), book.getImgPath());
    }

    @Override
    public Book selectBookByPrimaryKey(Integer id) throws Exception {
        String sql = "select book_id as bookId , book_name as bookName,author,price , sales , stock , img_path as imgPath from t_book where book_id = ?";
        return getBean(Book.class, sql, id);
    }

    @Override
    public void updateBook(Book book) throws Exception {
        //暂时不修改图片路径
        String sql = "update t_book set book_name =? , price = ? ,author = ? ,sales=?,stock = ? where book_id = ?";
        update(sql, book.getBookName(), book.getPrice(), book.getAuthor(), book.getSales(), book.getStock(), book.getBookId());
    }

    @Override
    public void UpdateBookArr(Object[][] updateBookParamArr) throws Exception {
        String sql = "update t_book set sales = sales + ?,stock = stock - ? where book_id = ?";
        batchUpdate(sql, updateBookParamArr);
    }
}

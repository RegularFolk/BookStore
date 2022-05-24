package com.atguigu.service.impl;

import com.atguigu.bean.Book;
import com.atguigu.dao.BookDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public List<Book> getBookList() throws Exception {
        return bookDao.selectBookList();
    }

    @Override
    public void removeBook(Integer bookId) throws Exception {
        bookDao.deleteBook(bookId);
    }

    @Override
    public void saveBook(Book book) throws Exception {
        bookDao.insertBook(book);
    }

    @Override
    public Book getBookById(Integer id) throws Exception {
        return bookDao.selectBookByPrimaryKey(id);
    }

    @Override
    public void editBook(Book book) throws Exception {
        bookDao.updateBook(book);
    }
}

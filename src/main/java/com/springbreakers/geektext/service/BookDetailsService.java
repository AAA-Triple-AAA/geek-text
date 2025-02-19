package com.springbreakers.geektext.service;
import com.springbreakers.geektext.model.Author;
import com.springbreakers.geektext.model.Book;
import com.springbreakers.geektext.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDetailsService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDetailsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER);
    }

    /*
    public List<Book> getBooksByAuthor(int authorID) {
        String sql = "SELECT * FROM book WHERE author_id = ?";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER, authorID);
    }

    public List<Book> getBooksByISBN(String isbn) {
        String sql = "SELECT * FROM book WHERE isbn = ?";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER, isbn);
    }
    */

}

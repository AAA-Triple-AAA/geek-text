package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // MAYA'S METHODS
    public List<Book> getBooksByGenre(int genreId) {
        String sql = "SELECT * FROM book WHERE genre_id = ?";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER, genreId);
    }

    public List<Book> getTopSellers() {
        String sql = "SELECT * FROM book ORDER BY copies_sold DESC LIMIT 10";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER);
    }

    /*
    OPTIONAL SAMPLE METHOD HEADERS:

    public List<Book> getBooksByRating(int rating) {}

    public void setPublisherDiscount(String publisher) {}

    */


    /*
    JESSICA'S METHODS

    public List<Book> getBooksByAuthor(int authorId) {
        String sql = "SELECT * FROM book WHERE author_id = ?";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER, authorID);
    }

    public List<Book> getBooksByISBN(String isbn) {
        String sql = "SELECT * FROM book WHERE isbn = ?";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER, isbn);
    }
    */

    // GENERAL METHODS
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER);
    }
}

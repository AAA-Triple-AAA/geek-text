package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //JESSICA'S METHODS
    //Returns a single book based on the isbn.. isbns should be unique anyway. Handles case where book does not exist.
    public ResponseEntity<?> getBookByISBN(String isbn) {
        String sql = "SELECT * FROM book WHERE isbn = ?";
        List<Book> books = jdbcTemplate.query(sql, Book.BOOK_MAPPER, isbn);
        return books.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with ISBN: " + isbn)
                : ResponseEntity.ok(books.get(0));
    }

    /*
    public List<Book> getBooksByAuthor(int authorId) {
        String sql = "SELECT * FROM book WHERE author_id = ?";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER, authorID);
    }
    */

        /*
    OPTIONAL SAMPLE METHOD HEADERS:

    public List<Book> getBooksByRating(int rating) {}

    public void setPublisherDiscount(String publisher) {}

    */

    // GENERAL METHODS
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER);
    }
}

package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookSortingService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookSortingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER);
    }

    public int getGenreId(String genre) {
        String sql = "SELECT id FROM genre WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, genre);
    }

    public List<Book> getBooksByGenreId(int genre_id) {
        try {
            String sql = "SELECT * FROM book WHERE genre_id = ?";
            return jdbcTemplate.query(sql, Book.BOOK_MAPPER, genre_id);
        } catch(DataAccessException e) {
            throw new RuntimeException("Database error while retrieving books by genre", e);
        }
    }

    public List<Book> getTopSellers() {
        try {
            String sql = "SELECT * FROM book ORDER BY copies_sold DESC LIMIT 10";
            return jdbcTemplate.query(sql, Book.BOOK_MAPPER);
        } catch(DataAccessException e) {
            throw new RuntimeException("Database error while retrieving top sellers", e);
        }
    }

    public List<Book> getBooksByRating(double rating) {
        try {
            String sql = "SELECT b.id, b.isbn, b.description, b.year, b.price, b.copies_sold, " +
                    "b.publisher_id, b.author_id, b.title, b.genre_id, AVG(r.rating) AS rating " +
                    "FROM book AS b " +
                    "JOIN rating AS r ON b.id = r.book_id " +
                    "GROUP BY b.id " +
                    "HAVING AVG(r.rating) >= ?;";
            return jdbcTemplate.query(sql, Book.BOOK_MAPPER, rating);
        } catch(DataAccessException e) {
            throw new RuntimeException("Database error while retrieving books by rating", e);
        }
    }

    public int getPublisherId(String publisher) {
        String sql = "SELECT id FROM publisher WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, publisher);
    }

    public void discountBooksByPublisher(int publisherId, Double discount) {
        try {
            String sql = "UPDATE publisher SET discount = ? WHERE id = ?";
            jdbcTemplate.update(sql, discount, publisherId);
        } catch(DataAccessException e) {
            throw new RuntimeException("Database error while applying discount for publisher ID: " + publisherId, e);
        } catch(IllegalArgumentException e) {
            throw new RuntimeException("Invalid input: " + e.getMessage(), e);
        }
    }

}

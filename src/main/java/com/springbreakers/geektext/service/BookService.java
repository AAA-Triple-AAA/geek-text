package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.Book;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseEntity<?> getBookByISBN(String isbn) {
        String sql = "SELECT * FROM book WHERE isbn = ?";
        List<Book> books = jdbcTemplate.query(sql, Book.BOOK_MAPPER, isbn);
        return books.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with ISBN: " + isbn)
                : ResponseEntity.ok(books.get(0));
    }

    public ResponseEntity<?> getBooksByAuthor(int authorId) {
        String sql = "SELECT * FROM book WHERE author_id = ?";
        List<Book> books = jdbcTemplate.query(sql, Book.BOOK_MAPPER, authorId);
        return books.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with authorId: " + authorId)
                : ResponseEntity.ok(books);
    }

    public ResponseEntity<?> createBook(Book book) {
        String checkSql = "SELECT COUNT(*) FROM book WHERE isbn = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, book.getIsbn());

        if (count > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Book with ISBN " + book.getIsbn() + " already exists.");
        }

        String insertSql = "INSERT INTO book (isbn, title, description, year, price, copies_sold, genre_id, publisher_id, author_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(insertSql,
                    book.getIsbn(),
                    book.getTitle(),
                    book.getDescription(),
                    book.getYear(),
                    book.getPrice(),
                    book.getCopiesSold(),
                    book.getGenreId(),
                    book.getPublisherId(),
                    book.getAuthorId());
            return ResponseEntity.status(HttpStatus.CREATED).body("Book created successfully.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Failed to create a book. Please check genreId, publisherId, or authorId.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    // GENERAL METHODS
    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, Book.BOOK_MAPPER);
    }
}

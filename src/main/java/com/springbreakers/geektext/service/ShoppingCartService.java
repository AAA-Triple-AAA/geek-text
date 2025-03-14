package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.Book;
import com.springbreakers.geektext.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShoppingCartService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ResponseEntity<String> addBook(String userId, String bookId) {
        ResponseEntity<String> validationResponse = validateUserExists(userId);
        if (validationResponse != null) {
            return validationResponse;
        }

        String sql = "INSERT INTO shopping_cart VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, Integer.parseInt(userId), Integer.parseInt(bookId));
            return ResponseEntity.status(HttpStatus.CREATED).body("Book added to shopping cart successfully.");
        } catch (DataIntegrityViolationException e) {
            String rootCauseMessage = e.getMostSpecificCause().getMessage();
            if (rootCauseMessage.contains("violates unique constraint")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Book already exists in shopping cart.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No records for book.");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper book id format.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    public ResponseEntity<String> removeBook(String userId, String bookId) {
        ResponseEntity<String> validationResponse = validateUserExists(userId);
        if (validationResponse != null) {
            return validationResponse;
        }

        String sql = "DELETE FROM shopping_cart WHERE user_id=? AND book_id=?";
        try {
            return jdbcTemplate.update(sql, Integer.parseInt(userId), Integer.parseInt(bookId)) == 0
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found in the shopping cart.")
                    : ResponseEntity.status(HttpStatus.OK).body("Book removed from shopping cart successfully.");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper book ID format.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    public ResponseEntity<?> getBooks(String userId) {
        ResponseEntity<String> validationResponse = validateUserExists(userId);
        if (validationResponse != null) {
            return validationResponse;
        }

        String sql = "SELECT id, isbn, title, description, year, price, copies_sold, genre_id, publisher_id, author_id FROM shopping_cart AS s JOIN book AS b ON s.book_id = b.id WHERE s.user_id = ?";
        List<Book> books = jdbcTemplate.query(sql, Book.BOOK_MAPPER, Integer.parseInt(userId));

        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    public ResponseEntity<?> getSubtotal(String userId) {
        ResponseEntity<String> validationResponse = validateUserExists(userId);
        if (validationResponse != null) {
            return validationResponse;
        }

        String sql = "SELECT SUM(b.price - (b.price * p.discount)) FROM book AS b JOIN publisher AS p ON b.publisher_id = p.id JOIN shopping_cart as s ON s.user_id = b.id WHERE s.user_id = ?";
        Double subtotal = jdbcTemplate.queryForObject(sql, Double.class, Integer.parseInt(userId));
        if (subtotal == null) subtotal = 0.0;
        return ResponseEntity.status(HttpStatus.OK).body(subtotal);
    }

    private ResponseEntity<String> validateUserExists(String userId) {
        String findUser = "SELECT * FROM \"user\" WHERE id = ?";
        try {
            Optional<User> user = Optional.ofNullable(
                    jdbcTemplate.queryForObject(findUser, User.USER_MAPPER, Integer.parseInt(userId))
            );
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper user ID format.");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
        return null;
    }
}

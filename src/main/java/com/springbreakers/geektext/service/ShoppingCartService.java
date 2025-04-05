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

    /**
     * Service method for adding a book to a user's shopping cart
     *
     * @param userId String value of the user's id
     * @param bookId String value of the book id
     * @return Status code response description
     */
    public ResponseEntity<String> addBook(String userId, String bookId) {
        // Check for user existence in the database
        ResponseEntity<String> validationResponse = validateUserExists(userId);
        if (validationResponse != null) return validationResponse; // Return error code if one is returned

        // SQL query that adds a book to a user's shopping cart
        String sql = "INSERT INTO shopping_cart VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, Integer.parseInt(userId), Integer.parseInt(bookId));
            return ResponseEntity.status(HttpStatus.CREATED).body("Book added to shopping cart successfully.");
        } catch (DataIntegrityViolationException e) {
            // In case of error in the execution of the query
            String rootCauseMessage = e.getMostSpecificCause().getMessage();
            if (rootCauseMessage.contains("violates unique constraint")) {
                // If a book and user record is being added again, the book is already in the user's shopping cart
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Book already exists in shopping cart.");
            } else {
                // Otherwise, the book is not in the database
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No records for book.");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper book id format.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    /**
     * Service method that removes a book from a user's shopping cart
     *
     * @param userId String value of the user's id
     * @param bookId String value of the book id
     * @return Status code response description
     */
    public ResponseEntity<String> removeBook(String userId, String bookId) {
        // Check for user existence in the database
        ResponseEntity<String> validationResponse = validateUserExists(userId);
        if (validationResponse != null) return validationResponse; // Return error code if one is returned

        // SQL query that removes a book from a user's shopping cart
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

    /**
     * Service method that returns the books in a user's shopping cart
     *
     * @param userId String value of the user's id
     * @return Error code response or list of books
     */
    public ResponseEntity<?> getBooks(String userId) {
        // Check for user existence in the database
        ResponseEntity<String> validationResponse = validateUserExists(userId);
        if (validationResponse != null) return validationResponse; // Return error code if one is returned

        // SQL query gets all the books in a user's shopping cart
        String sql = "SELECT id, isbn, title, description, year, price, copies_sold, genre_id, publisher_id, author_id FROM shopping_cart AS s JOIN book AS b ON s.book_id = b.id WHERE s.user_id = ?";
        List<Book> books = jdbcTemplate.query(sql, Book.BOOK_MAPPER, Integer.parseInt(userId));

        return ResponseEntity.status(HttpStatus.OK).body(books); // Return successful status code and data
    }

    /**
     * Service method that reads and returns a user's cart subtotal from the database.
     *
     * @param userId String value of the user's id
     * @return Error code response or double representing cart subtotal
     */
    public ResponseEntity<?> getSubtotal(String userId) {
        // Check for user existence in the database
        ResponseEntity<String> validationResponse = validateUserExists(userId);
        if (validationResponse != null) return validationResponse; // Return error code if one is returned

        // SQL query that calculates the sum a user's shopping cart
        String sql = "SELECT SUM(b.price - (b.price * p.discount)) FROM book AS b JOIN publisher AS p ON b.publisher_id = p.id JOIN shopping_cart as s ON s.user_id = b.id WHERE s.user_id = ?";
        Double subtotal = jdbcTemplate.queryForObject(sql, Double.class, Integer.parseInt(userId)); // Process the query

        if (subtotal == null) subtotal = 0.0; // Set to default 0.0 is user has no books in cart

        return ResponseEntity.status(HttpStatus.OK).body(subtotal); // Return successful status code and data
    }

    /**
     * Utility function to determine if a user exists in the database.
     *
     * @param userId String value of the user's id
     * @return Error code response or nothing if user exists
     */
    private ResponseEntity<String> validateUserExists(String userId) {
        // SQL query to get all users of the same id (should be 0 or 1)
        String findUser = "SELECT * FROM \"user\" WHERE id = ?";
        try {
            // Get the user returned from the database
            Optional<User> user = Optional.ofNullable(jdbcTemplate.queryForObject(findUser, User.USER_MAPPER, Integer.parseInt(userId)));

            // Return NOT_FOUND if no user is returned from the database query
            if (user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (NumberFormatException e) {
            // Return BAD_REQUEST if userId is not purely numeric
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper user ID format.");
        } catch (EmptyResultDataAccessException e) {
            // Return NOT_FOUND if no user is returned from the database query
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
        return null;
    }
}

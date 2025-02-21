package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShoppingCartService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ResponseEntity<String> addBookToShoppingCart(String userId, String bookId) {
        String sql = "INSERT INTO shopping_cart VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, Integer.parseInt(userId), Integer.parseInt(bookId));
            return ResponseEntity.status(HttpStatus.CREATED).body("Book added to shopping cart successfully.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper user id or book id format.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    public ResponseEntity<String> removeBookFromShoppingCart(String userId, String bookId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id=? AND book_id=?";
        try {
            return jdbcTemplate.update(sql, Integer.parseInt(userId), Integer.parseInt(bookId)) == 0
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found in the shopping cart.")
                    : ResponseEntity.status(HttpStatus.OK).body("Book removed from shopping cart successfully.");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper user ID or book ID format.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    public List<ShoppingCart> getShoppingCarts() {
        String sql = "SELECT * FROM shopping_cart";
        return jdbcTemplate.query(sql, ShoppingCart.SHOPPING_CART_MAPPER);
    }
}

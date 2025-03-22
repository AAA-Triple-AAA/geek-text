package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WishlistService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wishlist> getWishlists() {
        String sql = "SELECT * FROM wishlist";
        return jdbcTemplate.query(sql, Wishlist.WISHLIST_MAPPER);
    }

    public void createWishlist(int userId, String wishlistName) {
        String countSql = "SELECT COUNT(*) FROM wishlist WHERE user_id = ?";
        int count = jdbcTemplate.queryForObject(countSql, Integer.class, userId);
        if (count >= 3) {
            throw new IllegalStateException("User already has 3 wishlists.");
        }

        String nameSql = "SELECT COUNT(*) FROM wishlist WHERE user_id = ? AND name = ?";
        int nameCount = jdbcTemplate.queryForObject(nameSql, Integer.class, userId, wishlistName);
        if (nameCount > 0) {
            throw new IllegalArgumentException("Wishlist name already exists for this user.");
        }

        String insertSql = "INSERT INTO wishlist (name, user_id) VALUES (?, ?)";
        jdbcTemplate.update(insertSql, wishlistName, userId);
    }

    public void addBookToWishlist(int wishlistId, int bookId) {
        String insertBookSql = "INSERT INTO wishlist_book VALUES (?, ?)";
        jdbcTemplate.update(insertBookSql, wishlistId, bookId);
    }

    public void moveBookToCart(int wishlistId, int bookId, int userId) {
        String deleteSql = "DELETE FROM wishlist_book WHERE wish_list_id = ? AND book_id = ?";
        int rowsAffected = jdbcTemplate.update(deleteSql, wishlistId, bookId);

        String insertCartSql = "INSERT INTO shopping_cart (user_id, book_id) VALUES (?, ?)";
        jdbcTemplate.update(insertCartSql, userId, bookId);
    }

    public List<Integer> getBooksInWishlist(int wishlistId) {
        String sql = "SELECT book_id FROM wishlist_book WHERE wish_list_id = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, wishlistId);
    }
}

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
    }
}

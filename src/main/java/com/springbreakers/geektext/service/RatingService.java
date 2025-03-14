package com.springbreakers.geektext.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.springbreakers.geektext.model.Rating;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RatingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Double getBookAvgRating(int bookId) {
        String sql = "SELECT AVG(rating) FROM rating WHERE book_id=?";
        return jdbcTemplate.queryForObject(sql, Double.class, bookId);
    }

    public Optional<Rating> addBookRating(int bookId, int userId, int rating) {
        String sql = "INSERT INTO rating (rating, user_id, book_id) VALUES (?, ?, ?)";
        int rowsChanged = jdbcTemplate.update(sql, rating, userId, bookId);
        if(rowsChanged == 1) {
            return getBookRating(bookId, userId);
        }
        return Optional.empty();
    }

    public Optional<Rating> getBookRating(int bookId, int userId) {
        String sql = "SELECT * FROM rating WHERE book_id=? AND user_id=?";
        try {
            Rating rating = jdbcTemplate.queryForObject(sql, Rating.RATING_MAPPER, bookId, userId);
            return Optional.of(rating);
        } catch(Exception e) {
            return Optional.empty();
        }
    }
}

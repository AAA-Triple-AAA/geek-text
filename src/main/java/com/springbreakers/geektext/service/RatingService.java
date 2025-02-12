package com.springbreakers.geektext.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.springbreakers.geektext.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RatingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

   public List<Rating> getRatings() {
       String sql = "SELECT * FROM rating";
       return jdbcTemplate.query(sql, Rating.RATING_MAPPER);
   }
}

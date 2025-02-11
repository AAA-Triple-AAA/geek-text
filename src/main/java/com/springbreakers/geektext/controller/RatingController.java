package com.springbreakers.geektext.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbreakers.geektext.model.Rating;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController
{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RatingController(JdbcTemplate jdbcTemplate) {
       this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<Rating> getRatings() {
        String sql = "SELECT * FROM rating";
        return jdbcTemplate.query(sql, Rating.ROW_MAPPER);
    }
}

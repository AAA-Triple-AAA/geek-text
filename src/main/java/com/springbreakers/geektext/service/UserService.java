package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsers() {
        String sql = "SELECT * FROM \"user\"";
        return jdbcTemplate.query(sql, User.USER_MAPPER);
    }
}

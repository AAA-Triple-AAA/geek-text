package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.User;
import com.springbreakers.geektext.model.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void createUser(User user) {
        String sql = "INSERT INTO \"user\" (username, password, first_name, last_name, email, address) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getAddress());
    }

    public Optional<User> getUser(String username) {
        String sql = "SELECT * FROM \"user\" WHERE username = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, User.USER_MAPPER, username);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateUser(User user) {
        String sql = "UPDATE \"user\" SET password = ?, first_name = ?, last_name = ?, address = ?, role = ?, session_api_key = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getRole(),
                user.getSessionApiKey(),
                user.getId()
        );
    }

    public boolean addCreditCard(CreditCard creditCard) {
        String checkSql = "SELECT COUNT(*) FROM \"credit_card\" WHERE number = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, creditCard.getNumber());
        if (count > 0) {return false;}

        String sql = "INSERT INTO credit_card (card_holder, number, cvv, zip, user_id) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                creditCard.getCardHolder(),
                creditCard.getNumber(),
                creditCard.getCvv(),
                creditCard.getZip(),
                creditCard.getUserId()
        );
        return true;
    }

}
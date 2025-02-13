package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<ShoppingCart> getShoppingCarts() {
        String sql = "SELECT * FROM shopping_cart";
        return jdbcTemplate.query(sql, ShoppingCart.SHOPPING_CART_MAPPER);
    }
}

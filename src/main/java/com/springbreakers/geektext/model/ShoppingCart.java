package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class ShoppingCart {
    private Integer userId;
    private Integer bookId;

    public ShoppingCart(Integer userId, Integer bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public ShoppingCart() {}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public static final RowMapper<ShoppingCart> SHOPPING_CART_MAPPER = (rs, rowNum) -> new ShoppingCart(rs.getInt("user_id"), rs.getInt("book_id"));
}

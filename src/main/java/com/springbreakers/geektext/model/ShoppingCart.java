package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class ShoppingCart {
    private int userId;
    private int bookId;

    public ShoppingCart(int userId, int bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public static final RowMapper<ShoppingCart> SHOPPING_CART_MAPPER = (rs, rowNum) ->
            new ShoppingCart(rs.getInt("user_id"), rs.getInt("book_id"));
}

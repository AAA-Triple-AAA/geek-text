package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class WishlistBook {
    private int wishlistId;
    private int bookId;

    public WishlistBook(int wishlistId, int bookId) {
        this.wishlistId = wishlistId;
        this.bookId = bookId;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public static final RowMapper<WishlistBook> WISHLIST_BOOK_MAPPER = (rs, rowNum) -> {
        return new WishlistBook(rs.getInt("wish_list_id"), rs.getInt("book_id"));
    };
}

package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

public class WishlistBook {
    private int wishlistID;
    private int bookID;

    public WishlistBook(int wishlistID, int bookID) {
        this.wishlistID = wishlistID;
        this.bookID = bookID;
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public static final RowMapper<WishlistBook> WISHLIST_BOOK_MAPPER = (rs, rowNum) -> {
        return new WishlistBook(rs.getInt("wish_list_id"), rs.getInt("book_id"));
    };
}

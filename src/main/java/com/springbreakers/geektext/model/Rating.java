package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;

public class Rating {
    private int userId;
    private int bookId;
    private int rating;
    private Timestamp datestamp;

    public Rating(int userId, int bookId, int rating, Timestamp datestamp) {
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.datestamp = datestamp;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Timestamp getDatestamp() {
        return datestamp;
    }

    public void setDatestamp(Timestamp datestamp) {
        this.datestamp = datestamp;
    }

    @Override
    public String toString() {
        return "Rating{" + "userId=" + userId + ", bookId=" + bookId + ", rating=" + rating + ", datestamp=" + datestamp + '}';
    }

    public static final RowMapper<Rating> RATING_MAPPER = (rs, rowNum) -> {
        return new Rating(rs.getInt("user_id"),
                rs.getInt("book_id"),
                rs.getInt("rating"),
                rs.getTimestamp("datestamp"));
    };
}

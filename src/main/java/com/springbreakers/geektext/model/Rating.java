package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;

public class Rating {
    private int userID;
    private int bookID;
    private int rating;
    private Timestamp datestamp;

    public Rating(int userID, int bookID, int rating, Timestamp datestamp) {
        this.userID = userID;
        this.bookID = bookID;
        this.rating = rating;
        this.datestamp = datestamp;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
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
        return "Rating{" + "userId=" + userID + ", bookId=" + bookID + ", rating=" + rating + ", datestamp=" + datestamp + '}';
    }

    public static final RowMapper<Rating> RATING_MAPPER = (rs, rowNum) -> {
        return new Rating(rs.getInt("user_id"),
                rs.getInt("book_id"),
                rs.getInt("rating"),
                rs.getTimestamp("datestamp"));
    };
}

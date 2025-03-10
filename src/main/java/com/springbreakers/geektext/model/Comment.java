package com.springbreakers.geektext.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;

public class Comment {
    private int id;
    private String comment;
    private Timestamp timestamp;
    private int userId;
    private int bookId;

    public Comment(int id, String comment, Timestamp timestamp, int userId, int bookId) {
        this.id = id;
        this.comment = comment;
        this.timestamp = timestamp;
        this.userId = userId;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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

    public static final RowMapper<Comment> COMMENT_MAPPER = (rs, rowNum) -> {
        return new Comment(rs.getInt("id"),
                rs.getString("comment"),
                rs.getTimestamp("datestamp"),
                rs.getInt("user_id"),
                rs.getInt("book_id"));
    };
}

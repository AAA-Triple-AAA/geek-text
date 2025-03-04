package com.springbreakers.geektext.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.springbreakers.geektext.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Comment> getBookComments(int bookId) {
        String sql = "SELECT * FROM comment WHERE book_id=?";
        return jdbcTemplate.query(sql, Comment.COMMENT_MAPPER, bookId);
    }
}

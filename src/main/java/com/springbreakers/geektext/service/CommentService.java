package com.springbreakers.geektext.service;

import com.springbreakers.geektext.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.springbreakers.geektext.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Comment> getBookComments(int bookId) {
        // Attempt to retrieve book first to determine if it exists
        // If the book does not exist, SQL exception will be thrown to result in a 404
        String bookSql = "SELECT * FROM book WHERE id = ?";
        jdbcTemplate.queryForObject(bookSql, Book.BOOK_MAPPER, bookId);

        // Returns list of comments, which may be empty if there are no comments
        String sql = "SELECT * FROM comment WHERE book_id=?";
        return jdbcTemplate.query(sql, Comment.COMMENT_MAPPER, bookId);
    }

    public Optional<Comment> addBookComment(int bookId, int userId, String comment) {
        String sql = "INSERT INTO comment (comment, user_id, book_id) VALUES (?, ?, ?)";
        int rowsChanged = jdbcTemplate.update(sql, comment, userId, bookId);

        if(rowsChanged == 1) {
            return getBookComment(bookId, userId);
        }
        return Optional.empty();
    }

    public Optional<Comment> updateBookComment(int id, String comment) {
        String sql = "UPDATE comment SET comment=? WHERE id=?";
        int rowsChanged = jdbcTemplate.update(sql, comment, id);

        if(rowsChanged == 1) {
            return getBookCommentById(id);
        }
        return Optional.empty();
    }

    public Optional<Comment> getBookComment(int bookId, int userId) {
        String sql = "SELECT * FROM Comment WHERE book_id=? AND user_id=?";
        try {
            Comment comment = jdbcTemplate.queryForObject(sql, Comment.COMMENT_MAPPER, bookId, userId);
            return Optional.of(comment);
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Comment> getBookCommentById(int id) {
        String sql = "SELECT * FROM Comment WHERE id=?";
        try {
            Comment comment = jdbcTemplate.queryForObject(sql, Comment.COMMENT_MAPPER, id);
            return Optional.of(comment);
        } catch(Exception e) {
            return Optional.empty();
        }
    }
}

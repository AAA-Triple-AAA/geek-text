package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.service.CommentService;
import com.springbreakers.geektext.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbreakers.geektext.model.Comment;
import com.springbreakers.geektext.model.Rating;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookRatingAndCommentingController
{
    private final RatingService ratingService;
    private final CommentService commentService;

    @Autowired
    public BookRatingAndCommentingController(RatingService ratingService, CommentService commentService) {
        this.ratingService = ratingService;
        this.commentService = commentService;
    }

    @GetMapping("/books/{bookId}/comments")
    public ResponseEntity<?> getBookComments(@PathVariable String bookId) {
        int id = 0;
        try {
            id = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
           return ResponseEntity.badRequest().body("ERROR: Invalid format");
        }
        List<Comment> comments = commentService.getBookComments(id);
        return ResponseEntity.ok(comments);
    }

    /*
    TODO:
    - Create rating for a book by a user on a 5-star scale with a datestamp
    - Create comment for a book by a user with a datestamp
    - Retrieve average rating for a book
     */
}

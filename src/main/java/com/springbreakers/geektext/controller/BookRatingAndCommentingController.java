package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.service.CommentService;
import com.springbreakers.geektext.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Comment> getBookComments(@PathVariable int bookId) {
        return commentService.getBookComments(bookId);
    }
    /*
    TODO:
    - Create rating for a book by a user on a 5-star scale with a datestamp
    - Create comment for a book by a user with a datestamp
    - Retrieve a list of all comments for a particular book
    - Retrieve average rating for a book
     */
}

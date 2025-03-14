package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.service.CommentService;
import com.springbreakers.geektext.service.RatingService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springbreakers.geektext.model.Comment;
import com.springbreakers.geektext.model.Rating;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/books/{bookId}/rating")
    public ResponseEntity<?> getBookRating(@PathVariable String bookId) {
        int id;
        try {
            id = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("ERROR: Invalid format");
        }

        Double rating = ratingService.getBookAvgRating(id);
        if (rating == null) {
            return ResponseEntity.notFound().build();
        }

        // Ensure rating is always only 1 decimal place
        rating = (double)Math.round(rating * 10) / 10;

        return ResponseEntity.ok(Map.of("rating", rating));
    }

    @GetMapping("/books/{bookId}/comments")
    public ResponseEntity<?> getBookComments(@PathVariable String bookId) {
        int id;
        try {
            id = Integer.parseInt(bookId);
        } catch (NumberFormatException e) {
           return ResponseEntity.badRequest().body("ERROR: Invalid format");
        }
        List<Comment> comments = commentService.getBookComments(id);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/books/{bookId}/rating")
    public ResponseEntity<?> addBookRating(@PathVariable String bookId, @RequestParam String userId, @RequestParam String rating) {
        int validBookId, validUserId, validRating;

        try {
            validBookId = Integer.parseInt(bookId);
            validUserId = Integer.parseInt(userId);
            validRating = Integer.parseInt(rating);
        } catch (NumberFormatException e) {
            throw new DataIntegrityViolationException("Invalid format");
        }

        Optional<Rating> newRating = ratingService.addBookRating(validBookId, validUserId, validRating);
        if (newRating.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.created(URI.create("/books/" + bookId + "/rating")).build();
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    public Map<String, String> dataConflict() {
       return Map.of("error", "Invalid format or data does not exist");
    }

    @ResponseStatus(value=HttpStatus.CONFLICT)
    @ExceptionHandler({SQLException.class, DuplicateKeyException.class})
    @ResponseBody
    public Map<String, String> alreadyExists() {
        return Map.of("error", "Resource already exists");
    }

    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseBody
    public Map<String, String> resourceNotFound() {
        return Map.of("error", "Resource not found");
    }

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Map<String, String> otherErrors() {
        return Map.of("error", "Internal server error");
    }


    /*
    TODO:
    - Create comment for a book by a user with a datestamp
     */
}

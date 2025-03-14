package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.service.CommentService;
import com.springbreakers.geektext.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
public class BookRatingAndCommentingController {
    private final RatingService ratingService;
    private final CommentService commentService;

    @Autowired
    public BookRatingAndCommentingController(RatingService ratingService, CommentService commentService) {
        this.ratingService = ratingService;
        this.commentService = commentService;
    }

    /*
     * Book rating handler methods
     */

    @GetMapping("/books/{bookId}/rating")
    public ResponseEntity<?> getBookRating(@PathVariable String bookId) {
        int id;
        try {
            id = Integer.parseInt(bookId);
        } catch(NumberFormatException e) {
            return ResponseEntity.badRequest().body("ERROR: Invalid format");
        }

        Double rating = ratingService.getBookAvgRating(id);
        if(rating == null) {
            return ResponseEntity.notFound().build();
        }

        // Ensure rating is always only 1 decimal place
        rating = (double) Math.round(rating * 10) / 10;

        return ResponseEntity.ok(Map.of("rating", rating));
    }

    @PostMapping("/books/{bookId}/ratings")
    public ResponseEntity<?> addBookRating(@PathVariable String bookId, @RequestParam String userId, @RequestParam String rating) {
        int validBookId, validUserId, validRating;

        // User input validation
        try {
            validBookId = Integer.parseInt(bookId);
            validUserId = Integer.parseInt(userId);
            validRating = Integer.parseInt(rating);
        } catch(NumberFormatException e) {
            throw new DataIntegrityViolationException("Invalid format");
        }

        Optional<Rating> newRating = ratingService.addBookRating(validBookId, validUserId, validRating);
        // If a new rating object is not returned, the operation was not successful
        if(newRating.isEmpty()) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Rating could not be created"));
        }

        return ResponseEntity.created(URI.create("/books/" + bookId + "/ratings?userId=" + userId)).build();
    }

    /*
     * Book comment hanLer methods
     */

    @GetMapping("/books/{bookId}/comments")
    public ResponseEntity<?> getBookComments(@PathVariable String bookId) {
        int id;
        try {
            id = Integer.parseInt(bookId);
        } catch(NumberFormatException e) {
            return ResponseEntity.badRequest().body("ERROR: Invalid format");
        }
        List<Comment> comments = commentService.getBookComments(id);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/books/{bookId}/comments")
    public ResponseEntity<?> addBookComment(@PathVariable String bookId, @RequestParam String userId, @RequestParam String comment) {
        int validBookId, validUserId;
        int maxChars = 2000;
        comment = comment.trim();

        // User input validation
        try {
            validBookId = Integer.parseInt(bookId);
            validUserId = Integer.parseInt(userId);
        } catch(NumberFormatException e) {
            throw new DataIntegrityViolationException("Invalid format");
        }
        if(comment.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Comment cannot be empty"));
        } else if(comment.length() > maxChars) {
            return ResponseEntity.badRequest().body(Map.of("error", "Comment cannot be longer than " + maxChars + " characters"));
        }

        Optional<Comment> newComment;
        Optional<Comment> previousComment = commentService.getBookComment(validBookId, validUserId);
        boolean updated = false;
        if(previousComment.isEmpty()) {
            newComment = commentService.addBookComment(validBookId, validUserId, comment);
        } else {
            // If resource already exists, update the comment instead
            newComment = commentService.updateBookComment(previousComment.get().getId(), comment);
            updated = true;
        }

        // If a new rating object is not returned, the operation was not successful
        if(newComment.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }

        return updated ? ResponseEntity.ok().build() : ResponseEntity.created(URI.create("/books/" + bookId + "/comments?userId=" + userId)).build();
    }

    /*
     * Error handling methods
     */

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public Map<String, String> handleInvalidParams() {
        return Map.of("error", "Invalid parameter(s)");
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    public Map<String, String> handleDataConflict() {
        return Map.of("error", "Invalid format or data does not exist");
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler({SQLException.class, DuplicateKeyException.class})
    @ResponseBody
    public Map<String, String> handleAlreadyExists() {
        return Map.of("error", "Resource already exists");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseBody
    public Map<String, String> handleResourceNotFound() {
        return Map.of("error", "Resource not found");
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Map<String, String> handleOtherErrors() {
        return Map.of("error", "Internal server error");
    }
}

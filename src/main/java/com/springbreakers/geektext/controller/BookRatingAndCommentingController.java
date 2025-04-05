package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.service.CommentService;
import com.springbreakers.geektext.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Get book's average rating",
            description = "Returns the average rating of a book"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = "object",
                            example = "{\"rating\": 4.5}"
                    )
            )
    )
    @GetMapping("/books/{bookId}/rating")
    public ResponseEntity<?> getBookRating(@Parameter(
            description = "ID of the book for which to get the average rating",
            required = true,
            example = "5",
            schema = @Schema(type = "integer")
    ) @PathVariable String bookId) {
        int id;
        try {
            id = Integer.parseInt(bookId);
        } catch(NumberFormatException e) {
            throw new DataIntegrityViolationException("Invalid format");
        }

        Double rating = ratingService.getBookAvgRating(id);
        if(rating == null) {
            throw new EmptyResultDataAccessException(1);
        }

        // Ensure rating is always only 1 decimal place
        rating = (double) Math.round(rating * 10) / 10;

        return ResponseEntity.ok(Map.of("rating", rating));
    }

    @Operation(
            summary = "Add a rating for a book",
            description = "Returns 201 HTTP status code if operation was successful"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Successfully created book rating"
    )
    @PostMapping("/books/{bookId}/ratings")
    public ResponseEntity<?> addBookRating(@Parameter(
            description = "ID of the book",
            required = true,
            example = "5",
            schema = @Schema(type = "integer")
    ) @PathVariable String bookId, @Parameter(
            description = "ID of the user",
            required = true,
            example = "5",
            schema = @Schema(type = "integer")
    ) @RequestParam String userId, @Parameter(
            description = "User's rating for the book",
            required = true,
            example = "3",
            schema = @Schema(type = "integer")
    ) @RequestParam String rating) {
        int validBookId, validUserId, validRating;

        // User input validation
        try {
            validBookId = Integer.parseInt(bookId);
            validUserId = Integer.parseInt(userId);
            validRating = Integer.parseInt(rating);
        } catch(NumberFormatException e) {
            throw new DataIntegrityViolationException("Invalid format");
        }

        // Create rating
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

    @Operation(
            summary = "Get book's comments",
            description = "Returns a list of comments made for a book"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Comment.class))
            )
    )
    @GetMapping("/books/{bookId}/comments")
    public ResponseEntity<?> getBookComments(@Parameter(
            description = "ID of the book for which to get the list of comments",
            required = true,
            example = "5",
            schema = @Schema(type = "integer")
    ) @PathVariable String bookId) {
        int id;
        try {
            id = Integer.parseInt(bookId);
        } catch(NumberFormatException e) {
            throw new DataIntegrityViolationException("Invalid format");
        }

        List<Comment> comments = commentService.getBookComments(id);
        return ResponseEntity.ok(comments);
    }

    @Operation(
            summary = "Create a comment for a book",
            description = "Returns 201 or 200 HTTP status codes if operation was successful"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Successfully created book comment"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successfully updated book comment"
    )
    @PostMapping("/books/{bookId}/comments")
    public ResponseEntity<?> addBookComment(@Parameter(
            description = "ID of the book",
            required = true,
            example = "5",
            schema = @Schema(type = "integer")
    ) @PathVariable String bookId, @Parameter(
            description = "ID of the user",
            required = true,
            example = "5",
            schema = @Schema(type = "integer")
    ) @RequestParam String userId, @Parameter(
            description = "Comment text",
            required = true,
            example = "This was a great book",
            schema = @Schema(type = "string")
    ) @RequestParam String comment) {
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

        // Create comment
        Optional<Comment> newComment;
        Optional<Comment> previousComment = commentService.getBookComment(validBookId, validUserId);
        boolean updated = false;
        if(previousComment.isEmpty()) {
            newComment = commentService.addBookComment(validBookId, validUserId, comment);
        } else {
            // When a comment already exists, update the comment instead, otherwise multiple comments will be created
            newComment = commentService.updateBookComment(previousComment.get().getId(), comment);
            updated = true;
        }

        // If a comment object is not returned, the operation was not successful
        if(newComment.isEmpty()) {
            return ResponseEntity.internalServerError().build();
        }

        return updated ? ResponseEntity.ok().build() : ResponseEntity.created(URI.create("/books/" + bookId + "/comments?userId=" + userId)).build();
    }

    /*
     * Error handling methods
     */

    @ApiResponse(
            responseCode = "400",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = "object",
                            example = "{\"error\": \"error message\"}"
                    )
            )
    )
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public Map<String, String> handleInvalidParams() {
        return Map.of("error", "Invalid parameter(s)");
    }

    @ApiResponse(
            responseCode = "400",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = "object",
                            example = "{\"error\": \"error message\"}"
                    )
            )
    )
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseBody
    public Map<String, String> handleDataConflict() {
        return Map.of("error", "Invalid format or data does not exist");
    }

    @ApiResponse(
            responseCode = "409",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = "object",
                            example = "{\"error\": \"error message\"}"
                    )
            )
    )
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler({SQLException.class, DuplicateKeyException.class})
    @ResponseBody
    public Map<String, String> handleAlreadyExists() {
        return Map.of("error", "Resource already exists");
    }

    @ApiResponse(
            responseCode = "404",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = "object",
                            example = "{\"error\": \"error message\"}"
                    )
            )
    )
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseBody
    public Map<String, String> handleResourceNotFound() {
        return Map.of("error", "Resource not found");
    }

    @ApiResponse(
            responseCode = "500",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            type = "object",
                            example = "{\"error\": \"error message\"}"
                    )
            )
    )
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Map<String, String> handleOtherErrors() {
        return Map.of("error", "Internal server error");
    }
}

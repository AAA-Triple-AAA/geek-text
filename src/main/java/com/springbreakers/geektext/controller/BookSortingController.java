package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.springbreakers.geektext.service.BookSortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookSortingController {
    private final BookSortingService bookSortingService;

    @Autowired
    public BookSortingController(BookSortingService bookService) {
        this.bookSortingService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookSortingService.getAllBooks();
    }

    @Operation(summary = "Get books by genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books matching the genre ID",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid genre ID", content = @Content)
    })
    @GetMapping("/genre")
    public ResponseEntity<?> getBooksByGenre(@RequestParam String genreId) {
        try {
            int parsedGenreId = Integer.parseInt(genreId);
            List<Book> books = bookSortingService.getBooksByGenreId(parsedGenreId);
            return ResponseEntity.ok(books);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid genre ID. Please enter a valid number.");
        } catch (Exception e) {
            return handleException("Error retrieving books by genre", e);
        }
    }

    @Operation(summary = "Get top 10 bestselling books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Top-selling books",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/top-sellers")
    public ResponseEntity<?> getTopSellers() {
        try {
            List<Book> books = bookSortingService.getTopSellers();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return handleException("Error retrieving top sellers", e);
        }
    }

    @Operation(summary = "Get books for a particular rating and higher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books with rating above the threshold",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "Invalid rating value", content = @Content)
    })
    @GetMapping("/rating")
    public ResponseEntity<?> getBooksByRating(@RequestParam String rating) {
        try {
            double ratingValue = Double.parseDouble(rating);
            List<Book> books = bookSortingService.getBooksByRating(ratingValue);
            return ResponseEntity.ok(books);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid rating value. Please enter a valid number.");
        } catch (Exception e) {
            return handleException("Error retrieving books by rating", e);
        }
    }

    @Operation(summary = "Apply discount to books by publisher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount applied successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/publishers/{publisher_id}")
    public ResponseEntity<?> discountBooksByPublisher(
            @PathVariable("publisher_id") String publisher_id,
            @RequestParam(required = false) String discount) {
        try {
            // Validate if the publisher_id is a valid number
            try {
                Integer.parseInt(publisher_id);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Invalid parameter: Publisher ID must be a number.");
            }

            // Validate if discount is a valid number if it's provided
            if (discount != null) {
                try {
                    Double.parseDouble(discount);
                } catch (NumberFormatException e) {
                    return ResponseEntity.badRequest().body("Invalid parameter: Discount must be a number.");
                }
            }

            // Convert and pass the validated parameters
            int publisherIdInt = Integer.parseInt(publisher_id);
            Double discountDouble = discount != null ? Double.parseDouble(discount) : null;

            bookSortingService.discountBooksByPublisher(publisherIdInt, discountDouble);
            return ResponseEntity.ok(Map.of("message", "Discount applied successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return handleException("Error applying discount to books by publisher", e);
        }
    }
    private ResponseEntity<Map<String, String>> handleException(String message, Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", message, "details", e.getMessage()));
    }

}

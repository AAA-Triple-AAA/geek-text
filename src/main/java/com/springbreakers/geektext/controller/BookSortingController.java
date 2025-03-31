package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.BookSorting;
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

    // Retrieve Books by Genre
    @GetMapping
    public List<BookSorting> getAllBooks() {
        return bookSortingService.getAllBooks();
    }

    @GetMapping("/genre")
    public ResponseEntity<?> getBooksByGenre(@RequestParam String genre_id) {
        try {
            int genreId = Integer.parseInt(genre_id);
            List<BookSorting> books = bookSortingService.getBooksByGenre(genreId);
            return ResponseEntity.ok(books);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid genre ID. Please enter a valid number.");
        } catch (Exception e) {
            return handleException("Error retrieving books by genre", e);
        }
    }

    @GetMapping("/top-sellers")
    public ResponseEntity<?> getTopSellers() {
        try {
            List<BookSorting> books = bookSortingService.getTopSellers();
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return handleException("Error retrieving top sellers", e);
        }
    }

    @GetMapping("/rating")
    public ResponseEntity<?> getBooksByRating(@RequestParam String rating) {
        try {
            double ratingValue = Double.parseDouble(rating);
            List<BookSorting> books = bookSortingService.getBooksByRating(ratingValue);
            return ResponseEntity.ok(books);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid rating value. Please enter a valid number.");
        } catch (Exception e) {
            return handleException("Error retrieving books by rating", e);
        }
    }

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

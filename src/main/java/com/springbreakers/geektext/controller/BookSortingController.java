package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.Book;
import com.springbreakers.geektext.service.BookSortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookSortingController {
    private final BookSortingService bookSortingService;

    @Autowired
    public BookSortingController(BookSortingService bookService) {
        this.bookSortingService = bookService;
    }

    // Retrieve Books by Genre
    @GetMapping
    public List<Book> getAllBooks() {
        return bookSortingService.getAllBooks();
    }

    @GetMapping("/books/{genre}")
    public ResponseEntity<?> getBooksByGenre(@PathVariable String genre) {
        try {
            int genreId = bookSortingService.getGenreId(genre);
            List<Book> books = bookSortingService.getBooksByGenreId(genreId);
            return ResponseEntity.ok(books);
        } catch(Exception e) {
            return handleException("Error retrieving books by genre", e);
        }
    }

    @GetMapping("/books/top-sellers")
    public ResponseEntity<?> getTopSellers() {
        try {
            List<Book> books = bookSortingService.getTopSellers();
            return ResponseEntity.ok(books);
        } catch(Exception e) {
            return handleException("Error retrieving top sellers", e);
        }
    }

    @GetMapping("/books")
    public ResponseEntity<?> getBooksByRating(@RequestParam String rating) {
        try {
            double ratingValue = Double.parseDouble(rating);
            List<Book> books = bookSortingService.getBooksByRating(ratingValue);
            return ResponseEntity.ok(books);
        } catch(NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid rating value. Please enter a valid number.");
        } catch(Exception e) {
            return handleException("Error retrieving books by rating", e);
        }
    }

    @PutMapping("/publishers/{publisher}")
    public ResponseEntity<?> discountBooksByPublisher(
            @PathVariable("publisher") String publisher,
            @RequestParam String discount) {
        try {
            Double discountDouble = discount != null ? Double.parseDouble(discount) : null;
            int publisherId = bookSortingService.getPublisherId(publisher);
            bookSortingService.discountBooksByPublisher(publisherId, discountDouble);
            return ResponseEntity.ok(Map.of("message", "Discount applied successfully"));
        } catch(NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid parameter: Publisher ID must be a number.");
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e) {
            return handleException("Error applying discount to books by publisher", e);
        }
    }

    private ResponseEntity<Map<String, String>> handleException(String message, Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", message, "details", e.getMessage()));
    }

}

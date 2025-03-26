package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.BookSorting;
import com.springbreakers.geektext.service.BookSortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/bookSorting")
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
    public List<BookSorting> getBooksByGenre(@RequestParam int genre_id) {
        return bookSortingService.getBooksByGenre(genre_id);
    }

    @GetMapping("/top-sellers")
    public List<BookSorting> getTopSellers() {
        return bookSortingService.getTopSellers();
    }

    @GetMapping("/rating")
    public List<BookSorting> getBooksByRating(@RequestParam double rating) {
        return bookSortingService.getBooksByRating(rating);
    }

    @PutMapping("/discount")
    public void discountBooksByPublisher(@RequestParam double discount_percent, @RequestParam int publisher_id) {
        bookSortingService.discountBooksByPublisher(discount_percent, publisher_id);
    }

}

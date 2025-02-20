package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.Book;
import com.springbreakers.geektext.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-sorting")
public class BookBrowsingAndSortingController {
    private final BookService bookService;

    @Autowired
    public BookBrowsingAndSortingController(BookService bookService) {
        this.bookService = bookService;
    }

    // Retrieve Books by Genre
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    
    @GetMapping("/{genreID}")
    public List<Book> getBooksByGenre(@PathVariable String genreID) {
        return bookService.getBooksByGenre(Integer.parseInt(genreID));
    }

    @GetMapping("/top-sellers")
    public List<Book> getTopSellers() {
        return bookService.getTopSellers();
    }
}

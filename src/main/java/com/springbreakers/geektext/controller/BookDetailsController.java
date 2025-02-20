package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.Book;
import com.springbreakers.geektext.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book-details")
public class BookDetailsController {
    private final BookService bookService;

    @Autowired
    public BookDetailsController(BookService bookService) {
        this.bookService = bookService;
    }

    // Function from service BookDetailsService
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

   /*
   TODO:
   Get a book by ISBN (isbn)
   Get a book associated w/ an Author, (author_id)
   Admin can create an author w/ first, last, bio, and publisher
   */
}

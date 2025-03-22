package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.Author;
import com.springbreakers.geektext.model.Book;
import com.springbreakers.geektext.service.AuthorService;
import com.springbreakers.geektext.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-details")
public class BookDetailsController {
    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookDetailsController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // Function from service BookDetailsService
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<?> getBookByISBN(@PathVariable String isbn){ return bookService.getBookByISBN(isbn); }

    @PostMapping("/create/authors")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> createAuthor(@RequestBody Author author){ return authorService.createAuthor(author); }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable int authorId){ return bookService.getBooksByAuthor(authorId); }

    @PostMapping("create/books")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createBook(@RequestBody Book book){ return bookService.createBook(book); }

}

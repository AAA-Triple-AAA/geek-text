package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.Author;
import com.springbreakers.geektext.model.Book;
import com.springbreakers.geektext.service.AuthorService;
import com.springbreakers.geektext.service.BookService;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
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
    public ResponseEntity<String> createAuthor(@RequestBody Author author){ return authorService.createAuthor(author); }


    /*
   TODO:
   DONE: Get a book by ISBN (isbn)... Book Id
   -SEMI-DONE: Admin can create an author w/ first, last, bio, and publisher... Author object
   FOLLOW-UP: Need to implement Admin through User
   -Admin can create a book with the ISBN, book name, book desc, price, author, publisher, year published, and copies sold.. Book object
   -Get a book associated w/ an Author, (author_id)
   */
}

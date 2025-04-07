package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.Author;
import com.springbreakers.geektext.model.Book;
import com.springbreakers.geektext.service.AuthorService;
import com.springbreakers.geektext.service.BookService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Get all books", description = "Returns a list of all available books.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Books retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Book.class))))
    })
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Operation(summary = "Get book by ISBN", description = "Returns a book by its ISBN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Book retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Book not found by given ISBN",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Book not found.")))
    })
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<?> getBookByISBN(
            @Parameter(description = "ISBN of the book", required = true, example = "978-3-16-148410-0")
            @PathVariable String isbn) {
        return bookService.getBookByISBN(isbn);
    }

    @Operation(summary = "Create new author", description = "Allows ADMIN to create a new author.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED: Author created successfully",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Author created successfully."))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: Invalid author data",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Invalid input.")))
    })
    @PostMapping("/author")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> createAuthor(
            @Parameter(description = "Author details", required = true)
            @RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    @Operation(summary = "Get books by author", description = "Returns all books by the specified author.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK: Books retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Book.class)))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND: Author not found",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Author not found.")))
    })
    @GetMapping("/author/{authorId}")
    public ResponseEntity<?> getBooksByAuthor(
            @Parameter(description = "Author ID", required = true, example = "1")
            @PathVariable int authorId) {
        return bookService.getBooksByAuthor(authorId);
    }

    @Operation(summary = "Create new book", description = "Allows ADMIN to create a new book.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED: Book created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST: Invalid book data",
                    content = @Content(mediaType = "text/plain",
                            schema = @Schema(example = "Invalid input.")))
    })
    @PostMapping("/books")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createBook(
            @Parameter(description = "Book details", required = true)
            @RequestBody Book book) {
        return bookService.createBook(book);
    }
}

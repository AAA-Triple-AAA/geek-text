package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.Book;
import com.springbreakers.geektext.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Operation(summary = "Adds a book to a user's cart", description = "User and book must exist")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "CREATED: Book added to shopping cart successfully.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "Book added to shopping cart successfully."))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST:\n - Improper book id format.\n - Improper user ID format.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "No records for book."))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND: User not found.\n - No records for book.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "User not found."))
                    }),
            @ApiResponse(
                    responseCode = "409",
                    description = "CONFLICT: Book already exists in shopping cart.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "Book already exists in shopping cart."))
                    })
    })
    @PostMapping("/{userId}/cart")
    public ResponseEntity<String> addCartBook(
            @Parameter(description = "ID of the user", required = true, example = "1", schema = @Schema(type = "integer"))
            @PathVariable String userId,
            @Parameter(description = "ID of the book", required = true, example = "1", schema = @Schema(type = "integer"))
            @PathParam("bookId") String bookId) {
        return shoppingCartService.addBook(userId, bookId);
    }

    @Operation(summary = "Removes a book from a user's cart", description = "User and book must exist")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK: Book removed from shopping cart successfully.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "Book removed from shopping cart successfully."))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST:\n - Improper book id format.\n - Improper user ID format.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "No records for book."))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND:\n - User not found.\n - Book not found in the shopping cart.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "User not found."))
                    })
    })
    @DeleteMapping("/{userId}/cart")
    public ResponseEntity<String> removeCartBook(
            @Parameter(description = "ID of the user", required = true, example = "2", schema = @Schema(type = "integer"))
            @PathVariable String userId,
            @Parameter(description = "ID of the book", required = true, example = "2", schema = @Schema(type = "integer"))
            @PathParam("bookId") String bookId) {
        return shoppingCartService.removeBook(userId, bookId);
    }

    @Operation(summary = "Returns a list of books in a user's cart", description = "User must exist")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK: User exists and shopping cart returned.",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Book.class)))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST: Improper user ID format.",
                    content = {@Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "Improper user ID format."))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND: User not found.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "User not found."))
                    }),
    })
    @GetMapping("/{userId}/cart")
    public ResponseEntity<?> getCart(
            @Parameter(description = "ID of the user", required = true, example = "3", schema = @Schema(type = "integer"))
            @PathVariable String userId) {
        ResponseEntity<?> response = shoppingCartService.getBooks(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @Operation(summary = "Returns the subtotal of a user's cart", description = "User must exist")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK: User exists and shopping cart subtotal returned.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = Double.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD REQUEST: Improper user ID format.",
                    content = {@Content(
                            mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "Improper user ID format."))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND: User not found.",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "User not found."))
                    }),
    })
    @GetMapping("/{userId}/cart/subtotal")
    public ResponseEntity<?> getCartSubtotal(
            @Parameter(description = "ID of the user", required = true, example = "4", schema = @Schema(type = "integer"))
            @PathVariable String userId) {
        ResponseEntity<?> response = shoppingCartService.getSubtotal(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}

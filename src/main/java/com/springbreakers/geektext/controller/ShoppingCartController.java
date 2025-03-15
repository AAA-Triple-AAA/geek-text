package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.service.ShoppingCartService;
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

    @PostMapping("/{userId}/cart")
    public ResponseEntity<String> addCartBook(@PathVariable String userId, @PathParam("bookId") String bookId) {
        return shoppingCartService.addBook(userId, bookId);
    }

    @DeleteMapping("/{userId}/cart")
    public ResponseEntity<String> removeCartBook(@PathVariable String userId, @PathParam("bookId") String bookId) {
        return shoppingCartService.removeBook(userId, bookId);
    }

    @GetMapping("/{userId}/cart")
    public ResponseEntity<?> getCart(@PathVariable String userId) {
        ResponseEntity<?> response = shoppingCartService.getBooks(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @GetMapping("/{userId}/cart/subtotal")
    public ResponseEntity<?> getCartSubtotal(@PathVariable String userId) {
        ResponseEntity<?> response = shoppingCartService.getSubtotal(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}

package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.Book;
import com.springbreakers.geektext.model.ShoppingCart;
import com.springbreakers.geektext.service.ShoppingCartService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/shopping-carts")
    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCartService.getShoppingCarts();
    }

    @PostMapping("/{userId}/cart")
    public ResponseEntity<String> addBookToShoppingCart(@PathVariable String userId, @PathParam("bookId") String bookId) {
        return shoppingCartService.addBookToShoppingCart(userId, bookId);
    }

    @DeleteMapping("/{userId}/cart")
    public ResponseEntity<String> removeBookFromShoppingCart(@PathVariable String userId, @PathParam("bookId") String bookId) {
        return shoppingCartService.removeBookFromShoppingCart(userId, bookId);
    }

    @GetMapping("/{userId}/cart")
    public ResponseEntity<?> getShoppingCarts(@PathVariable String userId) {
        ResponseEntity<List<Book>> response = shoppingCartService.getShoppingCartBooks(userId);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper user ID format");
        }
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/{userId}/cart/subtotal")
    public ResponseEntity<?> getShoppingCartSubtotal(@PathVariable String userId) {
        ResponseEntity<Double> response = shoppingCartService.getShoppingCartTotal(userId);
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Improper user ID format");
        }
        return ResponseEntity.ok(response.getBody());
    }
}

package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.ShoppingCart;
import com.springbreakers.geektext.service.ShoppingCartService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /*
    TODO: GET request for SUBTOTAL
    TODO: GET request to display LIST OF BOOKS in the shopping cart
     */
}

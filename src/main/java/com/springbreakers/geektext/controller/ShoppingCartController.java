package com.springbreakers.geektext.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @GetMapping
    public String getStatus() {
        return "This is the shopping cart.";
    }

    // TODO: GET request for SUBTOTAL

    // TODO: POST request to ADD BOOK to shopping cart

    // TODO: GET request to display LIST OF BOOKS in the shopping cart

    // TODO: DELETE request to REMOVE BOOK from user's shopping cart
}

package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.ShoppingCart;
import com.springbreakers.geektext.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /*
    TODO: GET request for SUBTOTAL
    TODO: POST request to ADD BOOK to shopping cart
    TODO: GET request to display LIST OF BOOKS in the shopping cart
    TODO: DELETE request to REMOVE BOOK from user's shopping cart
     */
}

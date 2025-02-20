package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.Wishlist;
import com.springbreakers.geektext.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistManagementController {
    private final WishlistService wishlistService;

    @Autowired
    public WishlistManagementController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping
    public List<Wishlist> getWishlists() {
        return wishlistService.getWishlists();
    }
}

package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.Wishlist;
import com.springbreakers.geektext.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<String> createWishlist(@RequestParam int userId, @RequestParam String wishlistName) {
        try {
            wishlistService.createWishlist(userId, wishlistName);
            return ResponseEntity.ok("Wishlist created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}

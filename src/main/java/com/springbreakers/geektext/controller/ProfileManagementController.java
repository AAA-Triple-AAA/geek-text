package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.User;
import com.springbreakers.geektext.model.CreditCard;
import com.springbreakers.geektext.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class ProfileManagementController {

    private final UserService userService;

    @Autowired
    public ProfileManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        Optional<User> user = userService.getUser(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{username}")
    public ResponseEntity<Void> updateUser(@PathVariable String username, @RequestBody User updatedUser) {
        Optional<User> existingUserOptional = userService.getUser(username);

        if (existingUserOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User existingUser = existingUserOptional.get();

        // Update fields except email
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getAddress() != null) {
            existingUser.setAddress(updatedUser.getAddress());
        }
        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }

        userService.updateUser(existingUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{username}/credit-cards")
    public ResponseEntity<String> addCreditCard(@PathVariable String username, @RequestBody CreditCard creditCard) {
        Optional<User> userOptional = userService.getUser(username);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        creditCard.setUserId(user.getId());

        boolean check = userService.addCreditCard(creditCard);
        if (check) {
            return new ResponseEntity<>("Credit card created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Credit card with this number already exists", HttpStatus.CONFLICT);
        }
    }
}

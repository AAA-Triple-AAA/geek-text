package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.model.User;
import com.springbreakers.geektext.model.CreditCard;
import com.springbreakers.geektext.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Gets all users", description = "Retrieves all user details")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK: Successfully retrieved list of users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}
            )
    })
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @Operation(summary = "Create a new user",
            description = "Creates a new user with username, password, and optional fields (name, email, home address)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "CREATED: User successfully created",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "User created successfully"))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "CONFLICT: User with this username already exists",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "User with username example already exists"))}
            )
    })
    @PostMapping
    public ResponseEntity<String> createUser(
            @Parameter(description = "User data", required = true, schema = @Schema(implementation = User.class))
            @RequestBody User user) {
        boolean success = userService.createUser(user);
        if (success) {
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User with username " + user.getUsername() + " already exists", HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Gets user by username", description = "Retrieves specific user's details, given their username")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK: Successfully retrieved user details",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND: User not found",
                    content = @Content
            )
    })
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(
            @Parameter(description = "Username of the user", required = true, example = "johnsmith", schema = @Schema(type = "string"))
            @PathVariable String username) {
        Optional<User> user = userService.getUser(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Update user fields", description = "Update any user field other than email, given username")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK: User successfully updated",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "User updated successfully"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND: User not found",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "User not found"))}
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "INTERNAL SERVER ERROR: Failed to update user",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "Failed to update user"))}
            )
    })
    @PatchMapping("/{username}")
    public ResponseEntity<String> updateUser(
            @Parameter(description = "Username of the user", required = true, example = "johnsmith", schema = @Schema(type = "string"))
            @PathVariable String username,
            @Parameter(description = "Updated user data", required = true, schema = @Schema(implementation = User.class))
            @RequestBody User updatedUser) {
        Optional<User> existingUserOptional = userService.getUser(username);

        if (existingUserOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
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

        boolean success = userService.updateUser(existingUser);
        if (success) {
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Add credit card to user", description = "Create a credit card, belonging to a specific user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "CREATED: Credit card successfully added",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "Credit card created"))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT FOUND: User not found",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "User not found"))}
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "CONFLICT: Credit card with this number already exists",
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class, example = "Credit card with this number already exists"))}
            )
    })
    @PostMapping("/{username}/credit-cards")
    public ResponseEntity<String> addCreditCard(
            @Parameter(description = "Username of the user", required = true, example = "johnsmith", schema = @Schema(type = "string"))
            @PathVariable String username,
            @Parameter(description = "Credit card data", required = true, schema = @Schema(implementation = CreditCard.class))
            @RequestBody CreditCard creditCard) {
        Optional<User> userOptional = userService.getUser(username);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        creditCard.setUserId(user.getId());

        boolean success = userService.addCreditCard(creditCard);
        if (success) {
            return new ResponseEntity<>("Credit card created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Credit card with this number already exists", HttpStatus.CONFLICT);
        }
    }
}
package com.sdetcommerce.backend.controller;

import com.sdetcommerce.backend.dto.LoginRequest;
import com.sdetcommerce.backend.dto.LoginResponse;
import com.sdetcommerce.backend.dto.UserRegistrationRequest;
import com.sdetcommerce.backend.dto.UserResponse;
import com.sdetcommerce.backend.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {

        UserResponse user =
                userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                userService.loginUser(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(
            Authentication authentication) {

        String email = authentication.getName();

        UserResponse response =
                userService.getUserProfile(email);

        return ResponseEntity.ok(response);
    }
}
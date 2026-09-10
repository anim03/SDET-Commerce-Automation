package com.sdetcommerce.backend.controller;

import com.sdetcommerce.backend.dto.AddToCartRequest;
import com.sdetcommerce.backend.dto.CartItemResponse;
import com.sdetcommerce.backend.dto.UpdateCartItemRequest;
import com.sdetcommerce.backend.service.CartService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(
            CartService cartService) {

        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<CartItemResponse> addToCart(
            Authentication authentication,
            @Valid @RequestBody AddToCartRequest request) {

        CartItemResponse response =
                cartService.addToCart(
                        authentication.getName(),
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCart(
            Authentication authentication) {

        return ResponseEntity.ok(
                cartService.getCart(
                        authentication.getName()
                )
        );
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request) {

        return ResponseEntity.ok(
                cartService.updateCartItem(
                        authentication.getName(),
                        cartItemId,
                        request
                )
        );
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId) {

        cartService.removeCartItem(
                authentication.getName(),
                cartItemId
        );

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(
            Authentication authentication) {

        cartService.clearCart(
                authentication.getName()
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}
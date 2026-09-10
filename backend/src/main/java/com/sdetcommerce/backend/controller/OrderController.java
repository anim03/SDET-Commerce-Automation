package com.sdetcommerce.backend.controller;

import com.sdetcommerce.backend.dto.OrderResponse;
import com.sdetcommerce.backend.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(
            OrderService orderService) {

        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            Authentication authentication) {

        OrderResponse response =
                orderService.createOrder(
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(
            Authentication authentication) {

        return ResponseEntity.ok(
                orderService.getOrders(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            Authentication authentication,
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.getOrderById(
                        authentication.getName(),
                        orderId
                )
        );
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            Authentication authentication,
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.cancelOrder(
                        authentication.getName(),
                        orderId
                )
        );
    }
}
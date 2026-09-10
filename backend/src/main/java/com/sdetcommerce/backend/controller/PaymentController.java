package com.sdetcommerce.backend.controller;

import com.sdetcommerce.backend.dto.PaymentRequest;
import com.sdetcommerce.backend.dto.PaymentResponse;
import com.sdetcommerce.backend.service.PaymentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService) {

        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            Authentication authentication,
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response =
                paymentService.createPayment(
                        authentication.getName(),
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse>
    getPaymentByOrderId(
            Authentication authentication,
            @PathVariable Long orderId) {

        PaymentResponse response =
                paymentService.getPaymentByOrderId(
                        authentication.getName(),
                        orderId
                );

        return ResponseEntity.ok(response);
    }
}
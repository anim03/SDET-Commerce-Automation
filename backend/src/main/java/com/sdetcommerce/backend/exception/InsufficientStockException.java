package com.sdetcommerce.backend.exception;

public class InsufficientStockException
        extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }
}
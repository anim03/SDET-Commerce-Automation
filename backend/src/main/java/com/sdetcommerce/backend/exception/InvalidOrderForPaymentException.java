package com.sdetcommerce.backend.exception;

public class InvalidOrderForPaymentException
        extends RuntimeException {

    public InvalidOrderForPaymentException(
            String message) {

        super(message);
    }
}
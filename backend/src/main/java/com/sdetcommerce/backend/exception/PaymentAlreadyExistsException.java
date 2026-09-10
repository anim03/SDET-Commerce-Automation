package com.sdetcommerce.backend.exception;

public class PaymentAlreadyExistsException
        extends RuntimeException {

    public PaymentAlreadyExistsException(
            String message) {

        super(message);
    }
}
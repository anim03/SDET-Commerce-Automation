package com.sdetcommerce.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;




import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(InvalidCredentialsException.class)
public ResponseEntity<Map<String, Object>> handleInvalidCredentials(
        InvalidCredentialsException exception) {

    Map<String, Object> response = new HashMap<>();

    response.put("status", HttpStatus.UNAUTHORIZED.value());
    response.put("error", "Unauthorized");
    response.put("message", exception.getMessage());

    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(response);
}

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyExists(
            EmailAlreadyExistsException exception) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Conflict");
        response.put("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        Map<String, Object> response = new HashMap<>();

        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", "Validation failed");
        response.put("errors", errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
    @ExceptionHandler(ProductNotFoundException.class)
public ResponseEntity<Map<String, Object>> handleProductNotFound(
        ProductNotFoundException exception) {

    Map<String, Object> response = new HashMap<>();

    response.put("status", HttpStatus.NOT_FOUND.value());
    response.put("error", "Not Found");
    response.put("message", exception.getMessage());

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response);
}
@ExceptionHandler(CartItemNotFoundException.class)
public ResponseEntity<Map<String, Object>> handleCartItemNotFound(
        CartItemNotFoundException exception) {

    Map<String, Object> response =
            new HashMap<>();

    response.put(
            "status",
            HttpStatus.NOT_FOUND.value()
    );

    response.put(
            "error",
            "Not Found"
    );

    response.put(
            "message",
            exception.getMessage()
    );

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response);
}

@ExceptionHandler(InsufficientStockException.class)
public ResponseEntity<Map<String, Object>> handleInsufficientStock(
        InsufficientStockException exception) {

    Map<String, Object> response =
            new HashMap<>();

    response.put(
            "status",
            HttpStatus.CONFLICT.value()
    );

    response.put(
            "error",
            "Conflict"
    );

    response.put(
            "message",
            exception.getMessage()
    );

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(response);
}
@ExceptionHandler(EmptyCartException.class)
public ResponseEntity<Map<String, Object>> handleEmptyCart(
        EmptyCartException exception) {

    Map<String, Object> response =
            new HashMap<>();

    response.put("status", 400);
    response.put("error", "Bad Request");
    response.put(
            "message",
            exception.getMessage()
    );

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response);
}

@ExceptionHandler(OrderNotFoundException.class)
public ResponseEntity<Map<String, Object>> handleOrderNotFound(
        OrderNotFoundException exception) {

    Map<String, Object> response =
            new HashMap<>();

    response.put("status", 404);
    response.put("error", "Not Found");
    response.put(
            "message",
            exception.getMessage()
    );

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response);
}
@ExceptionHandler(OrderAlreadyCancelledException.class)
public ResponseEntity<Map<String, Object>> handleOrderAlreadyCancelled(
        OrderAlreadyCancelledException exception) {

    Map<String, Object> response =
            new HashMap<>();

    response.put("status", 409);
    response.put("error", "Conflict");
    response.put(
            "message",
            exception.getMessage()
    );

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(response);
}
@ExceptionHandler(PaymentAlreadyExistsException.class)
public ResponseEntity<Map<String, Object>>
handlePaymentAlreadyExists(
        PaymentAlreadyExistsException exception) {

    Map<String, Object> response = new HashMap<>();

    response.put(
            "status",
            HttpStatus.CONFLICT.value()
    );

    response.put(
            "error",
            "Conflict"
    );

    response.put(
            "message",
            exception.getMessage()
    );

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(response);
}

@ExceptionHandler(PaymentNotFoundException.class)
public ResponseEntity<Map<String, Object>>
handlePaymentNotFound(
        PaymentNotFoundException exception) {

    Map<String, Object> response = new HashMap<>();

    response.put(
            "status",
            HttpStatus.NOT_FOUND.value()
    );

    response.put(
            "error",
            "Not Found"
    );

    response.put(
            "message",
            exception.getMessage()
    );

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response);
}

@ExceptionHandler(InvalidOrderForPaymentException.class)
public ResponseEntity<Map<String, Object>>
handleInvalidOrderForPayment(
        InvalidOrderForPaymentException exception) {

    Map<String, Object> response = new HashMap<>();

    response.put(
            "status",
            HttpStatus.CONFLICT.value()
    );

    response.put(
            "error",
            "Conflict"
    );

    response.put(
            "message",
            exception.getMessage()
    );

    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(response);
}
}
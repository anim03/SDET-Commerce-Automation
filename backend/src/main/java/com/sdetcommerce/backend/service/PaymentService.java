package com.sdetcommerce.backend.service;

import com.sdetcommerce.backend.dto.PaymentRequest;
import com.sdetcommerce.backend.dto.PaymentResponse;
import com.sdetcommerce.backend.entity.Order;
import com.sdetcommerce.backend.entity.OrderStatus;
import com.sdetcommerce.backend.entity.Payment;
import com.sdetcommerce.backend.entity.PaymentStatus;
import com.sdetcommerce.backend.entity.User;
import com.sdetcommerce.backend.exception.InvalidOrderForPaymentException;
import com.sdetcommerce.backend.exception.OrderNotFoundException;
import com.sdetcommerce.backend.exception.PaymentAlreadyExistsException;
import com.sdetcommerce.backend.exception.PaymentNotFoundException;
import com.sdetcommerce.backend.repository.OrderRepository;
import com.sdetcommerce.backend.repository.PaymentRepository;
import com.sdetcommerce.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            UserRepository userRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PaymentResponse createPayment(
            String email,
            PaymentRequest request) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "User not found"
                                )
                        );

        Order order =
                orderRepository
                        .findByIdAndUser(
                                request.getOrderId(),
                                user
                        )
                        .orElseThrow(
                                () -> new OrderNotFoundException(
                                        "Order not found"
                                )
                        );

        if (order.getStatus() == OrderStatus.CANCELLED) {

            throw new InvalidOrderForPaymentException(
                    "Payment cannot be created for a cancelled order"
            );
        }

        if (paymentRepository.existsByOrderId(
                order.getId()
        )) {

            throw new PaymentAlreadyExistsException(
                    "Payment already exists for this order"
            );
        }

        String transactionId =
                "TXN-" + UUID.randomUUID();

        Payment payment =
                new Payment(
                        order,
                        order.getTotalAmount(),
                        request.getPaymentMethod()
                                .trim()
                                .toUpperCase(),
                        PaymentStatus.SUCCESS,
                        transactionId,
                        LocalDateTime.now()
                );

        Payment savedPayment =
                paymentRepository.save(payment);

        return mapToResponse(savedPayment);
    }

    public PaymentResponse getPaymentByOrderId(
            String email,
            Long orderId) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "User not found"
                                )
                        );

        Order order =
                orderRepository
                        .findByIdAndUser(
                                orderId,
                                user
                        )
                        .orElseThrow(
                                () -> new OrderNotFoundException(
                                        "Order not found"
                                )
                        );

        Payment payment =
                paymentRepository
                        .findByOrderId(
                                order.getId()
                        )
                        .orElseThrow(
                                () -> new PaymentNotFoundException(
                                        "Payment not found for this order"
                                )
                        );

        return mapToResponse(payment);
    }

    private PaymentResponse mapToResponse(
            Payment payment) {

        return new PaymentResponse(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus().name(),
                payment.getTransactionId(),
                payment.getCreatedAt()
        );
    }
}
package com.sdetcommerce.backend.service;

import com.sdetcommerce.backend.dto.OrderItemResponse;
import com.sdetcommerce.backend.dto.OrderResponse;
import com.sdetcommerce.backend.entity.CartItem;
import com.sdetcommerce.backend.entity.Order;
import com.sdetcommerce.backend.entity.OrderItem;
import com.sdetcommerce.backend.entity.OrderStatus;
import com.sdetcommerce.backend.entity.Product;
import com.sdetcommerce.backend.entity.User;
import com.sdetcommerce.backend.exception.EmptyCartException;
import com.sdetcommerce.backend.exception.InsufficientStockException;
import com.sdetcommerce.backend.exception.OrderAlreadyCancelledException;
import com.sdetcommerce.backend.exception.OrderNotFoundException;
import com.sdetcommerce.backend.repository.CartItemRepository;
import com.sdetcommerce.backend.repository.OrderRepository;
import com.sdetcommerce.backend.repository.ProductRepository;
import com.sdetcommerce.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(
            OrderRepository orderRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public OrderResponse createOrder(String email) {

        User user = findUserByEmail(email);

        List<CartItem> cartItems =
                cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new EmptyCartException(
                    "Cannot create order because cart is empty"
            );
        }

        /*
         * Validate stock for every cart item BEFORE
         * modifying inventory or creating the order.
         */
        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            if (cartItem.getQuantity() > product.getStock()) {

                throw new InsufficientStockException(
                        "Insufficient stock for product: "
                                + product.getName()
                );
            }
        }

        Order order =
                new Order(
                        user,
                        BigDecimal.ZERO,
                        OrderStatus.CREATED,
                        LocalDateTime.now()
                );

        BigDecimal totalAmount =
                BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {

            Product product =
                    cartItem.getProduct();

            BigDecimal subtotal =
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            cartItem.getQuantity()
                                    )
                            );

            OrderItem orderItem =
                    new OrderItem(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            cartItem.getQuantity(),
                            subtotal
                    );

            order.addItem(orderItem);

            totalAmount =
                    totalAmount.add(subtotal);

            product.setStock(
                    product.getStock()
                            - cartItem.getQuantity()
            );

            productRepository.save(product);
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder =
                orderRepository.save(order);

        cartItemRepository.deleteByUser(user);

        return mapToResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders(
            String email) {

        User user =
                findUserByEmail(email);

        return orderRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(
            String email,
            Long orderId) {

        User user =
                findUserByEmail(email);

        Order order =
                orderRepository
                        .findByIdAndUser(
                                orderId,
                                user
                        )
                        .orElseThrow(
                                () ->
                                        new OrderNotFoundException(
                                                "Order not found with ID: "
                                                        + orderId
                                        )
                        );

        return mapToResponse(order);
    }

    @Transactional
    public OrderResponse cancelOrder(
            String email,
            Long orderId) {

        User user =
                findUserByEmail(email);

        Order order =
                orderRepository
                        .findByIdAndUser(
                                orderId,
                                user
                        )
                        .orElseThrow(
                                () ->
                                        new OrderNotFoundException(
                                                "Order not found with ID: "
                                                        + orderId
                                        )
                        );

        if (order.getStatus()
                == OrderStatus.CANCELLED) {

            throw new OrderAlreadyCancelledException(
                    "Order is already cancelled"
            );
        }

        /*
         * Restore inventory for every item
         * contained in the cancelled order.
         */
        for (OrderItem orderItem :
                order.getItems()) {

            Product product =
                    productRepository
                            .findById(
                                    orderItem.getProductId()
                            )
                            .orElse(null);

            if (product != null) {

                product.setStock(
                        product.getStock()
                                + orderItem.getQuantity()
                );

                productRepository.save(product);
            }
        }

        order.setStatus(
                OrderStatus.CANCELLED
        );

        Order savedOrder =
                orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    private User findUserByEmail(
            String email) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () ->
                                new IllegalArgumentException(
                                        "Authenticated user not found"
                                )
                );
    }

    private OrderResponse mapToResponse(
            Order order) {

        List<OrderItemResponse> items =
                order.getItems()
                        .stream()
                        .map(
                                item ->
                                        new OrderItemResponse(
                                                item.getProductId(),
                                                item.getProductName(),
                                                item.getPrice(),
                                                item.getQuantity(),
                                                item.getSubtotal()
                                        )
                        )
                        .toList();

        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),

                // Enum internally,
                // String externally in API response
                order.getStatus().name(),

                order.getCreatedAt(),
                items
        );
    }
}
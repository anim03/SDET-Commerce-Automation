package com.sdetcommerce.backend.service;

import com.sdetcommerce.backend.dto.AddToCartRequest;
import com.sdetcommerce.backend.dto.CartItemResponse;
import com.sdetcommerce.backend.dto.UpdateCartItemRequest;
import com.sdetcommerce.backend.entity.CartItem;
import com.sdetcommerce.backend.entity.Product;
import com.sdetcommerce.backend.entity.User;
import com.sdetcommerce.backend.exception.CartItemNotFoundException;
import com.sdetcommerce.backend.exception.InsufficientStockException;
import com.sdetcommerce.backend.exception.ProductNotFoundException;
import com.sdetcommerce.backend.repository.CartItemRepository;
import com.sdetcommerce.backend.repository.ProductRepository;
import com.sdetcommerce.backend.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public CartItemResponse addToCart(
            String email,
            AddToCartRequest request) {

        User user = findUserByEmail(email);

        Product product =
                productRepository
                        .findById(request.getProductId())
                        .orElseThrow(
                                () -> new ProductNotFoundException(
                                        "Product not found with id: "
                                                + request.getProductId()
                                )
                        );

        CartItem cartItem =
                cartItemRepository
                        .findByUserAndProduct(user, product)
                        .orElse(null);

        int requestedQuantity = request.getQuantity();

        int finalQuantity =
                cartItem == null
                        ? requestedQuantity
                        : cartItem.getQuantity() + requestedQuantity;

        validateStock(
                product,
                finalQuantity
        );

        if (cartItem == null) {

            cartItem =
                    new CartItem(
                            user,
                            product,
                            requestedQuantity
                    );

        } else {

            cartItem.setQuantity(finalQuantity);
        }

        CartItem savedCartItem =
                cartItemRepository.save(cartItem);

        return mapToResponse(savedCartItem);
    }

    public List<CartItemResponse> getCart(String email) {

        User user = findUserByEmail(email);

        return cartItemRepository
                .findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CartItemResponse updateCartItem(
            String email,
            Long cartItemId,
            UpdateCartItemRequest request) {

        User user = findUserByEmail(email);

        CartItem cartItem =
                cartItemRepository
                        .findByIdAndUser(
                                cartItemId,
                                user
                        )
                        .orElseThrow(
                                () ->
                                        new CartItemNotFoundException(
                                                "Cart item not found with id: "
                                                        + cartItemId
                                        )
                        );

        validateStock(
                cartItem.getProduct(),
                request.getQuantity()
        );

        cartItem.setQuantity(
                request.getQuantity()
        );

        return mapToResponse(
                cartItemRepository.save(cartItem)
        );
    }

    public void removeCartItem(
            String email,
            Long cartItemId) {

        User user = findUserByEmail(email);

        CartItem cartItem =
                cartItemRepository
                        .findByIdAndUser(
                                cartItemId,
                                user
                        )
                        .orElseThrow(
                                () ->
                                        new CartItemNotFoundException(
                                                "Cart item not found with id: "
                                                        + cartItemId
                                        )
                        );

        cartItemRepository.delete(cartItem);
    }

    @Transactional
    public void clearCart(String email) {

        User user = findUserByEmail(email);

        cartItemRepository.deleteByUser(user);
    }

    private User findUserByEmail(String email) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () ->
                                new IllegalArgumentException(
                                        "Authenticated user not found"
                                )
                );
    }

    private void validateStock(
            Product product,
            Integer requestedQuantity) {

        if (requestedQuantity > product.getStock()) {

            throw new InsufficientStockException(
                    "Requested quantity "
                            + requestedQuantity
                            + " exceeds available stock "
                            + product.getStock()
            );
        }
    }

    private CartItemResponse mapToResponse(
            CartItem cartItem) {

        Product product =
                cartItem.getProduct();

        BigDecimal subtotal =
                product.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        cartItem.getQuantity()
                                )
                        );

        return new CartItemResponse(
                cartItem.getId(),
                product.getId(),
                product.getName(),
                product.getPrice(),
                cartItem.getQuantity(),
                subtotal
        );
    }
}
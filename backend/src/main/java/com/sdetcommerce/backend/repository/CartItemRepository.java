package com.sdetcommerce.backend.repository;

import com.sdetcommerce.backend.entity.CartItem;
import com.sdetcommerce.backend.entity.Product;
import com.sdetcommerce.backend.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProduct(
            User user,
            Product product
    );

    Optional<CartItem> findByIdAndUser(
            Long id,
            User user
    );

    void deleteByUser(User user);
}
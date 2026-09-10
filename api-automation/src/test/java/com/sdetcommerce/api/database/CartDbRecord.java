package com.sdetcommerce.api.database;

public class CartDbRecord {

    private final Long id;
    private final Long userId;
    private final Long productId;
    private final Integer quantity;

    public CartDbRecord(
            Long id,
            Long userId,
            Long productId,
            Integer quantity) {

        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
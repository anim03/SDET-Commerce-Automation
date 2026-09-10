package com.sdetcommerce.api.database;

import java.math.BigDecimal;

public class OrderItemDbRecord {

    private final Long id;
    private final Long orderId;
    private final Long productId;
    private final String productName;
    private final BigDecimal price;
    private final Integer quantity;
    private final BigDecimal subtotal;

    public OrderItemDbRecord(
            Long id,
            Long orderId,
            Long productId,
            String productName,
            BigDecimal price,
            Integer quantity,
            BigDecimal subtotal) {

        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
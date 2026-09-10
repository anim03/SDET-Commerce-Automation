package com.sdetcommerce.api.database;

import java.math.BigDecimal;

public class OrderDbRecord {

    private final Long id;
    private final Long userId;
    private final BigDecimal totalAmount;
    private final String status;

    public OrderDbRecord(
            Long id,
            Long userId,
            BigDecimal totalAmount,
            String status) {

        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }
}
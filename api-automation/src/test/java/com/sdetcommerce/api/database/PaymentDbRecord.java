package com.sdetcommerce.api.database;

import java.math.BigDecimal;

public class PaymentDbRecord {

    private final Long id;
    private final Long orderId;
    private final BigDecimal amount;
    private final String paymentMethod;
    private final String status;
    private final String transactionId;

    public PaymentDbRecord(
            Long id,
            Long orderId,
            BigDecimal amount,
            String paymentMethod,
            String status,
            String transactionId) {

        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.transactionId = transactionId;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public String getTransactionId() {
        return transactionId;
    }
}
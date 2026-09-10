package com.sdetcommerce.api.database;

import java.math.BigDecimal;

public class ProductDbRecord {

    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Integer stock;

    public ProductDbRecord(
            Long id,
            String name,
            String description,
            BigDecimal price,
            Integer stock) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }
}
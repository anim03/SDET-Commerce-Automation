package com.sdetcommerce.api.model;

import java.math.BigDecimal;

public class ProductRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    public ProductRequest() {
    }

    public ProductRequest(
            String name,
            String description,
            BigDecimal price,
            Integer stock) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
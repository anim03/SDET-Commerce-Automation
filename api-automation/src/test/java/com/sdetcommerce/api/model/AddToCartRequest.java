package com.sdetcommerce.api.model;

public class AddToCartRequest {

    private Long productId;
    private Integer quantity;

    public AddToCartRequest() {
    }

    public AddToCartRequest(
            Long productId,
            Integer quantity) {

        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
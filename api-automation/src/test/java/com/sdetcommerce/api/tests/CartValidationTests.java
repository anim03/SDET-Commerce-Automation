package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.model.AddToCartRequest;
import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class CartValidationTests extends BaseTest {

    @Test
    public void shouldRejectQuantityGreaterThanAvailableStock() {

        Long productId = null;

        try {

            String productName =
                    TestDataFactory.uniqueProductName(
                            "Low Stock Product"
                    );

            ProductRequest productRequest =
                    new ProductRequest(
                            productName,
                            "Insufficient stock test",
                            new BigDecimal("15.00"),
                            3
                    );

            Response productResponse =
                    productClient.createProduct(
                            adminToken,
                            productRequest
                    );

            Assert.assertEquals(
                    productResponse.statusCode(),
                    201
            );

            productId =
                    productResponse
                            .jsonPath()
                            .getLong("id");

            Response response =
                    cartClient.addToCart(
                            token,
                            new AddToCartRequest(
                                    productId,
                                    5
                            )
                    );

            Assert.assertEquals(
                    response.statusCode(),
                    409
            );

            Assert.assertEquals(
                    response
                            .jsonPath()
                            .getString("error"),
                    "Conflict"
            );

        } finally {

            TestDataCleanup.clearCartSafely(
                    cartClient,
                    token
            );

            TestDataCleanup.deleteProductIfExists(
                    productClient,
                    adminToken,
                    productId
            );
        }
    }

    @Test
    public void shouldRejectInvalidCartQuantity() {

        Response response =
                cartClient.addToCart(
                        token,
                        new AddToCartRequest(
                                1L,
                                0
                        )
                );

        Assert.assertEquals(
                response.statusCode(),
                400
        );

        Assert.assertEquals(
                response
                        .jsonPath()
                        .getString("error"),
                "Bad Request"
        );
    }

    @Test
    public void shouldReturn404ForUnknownProductWhenAddingToCart() {

        Response response =
                cartClient.addToCart(
                        token,
                        new AddToCartRequest(
                                Long.MAX_VALUE,
                                1
                        )
                );

        Assert.assertEquals(
                response.statusCode(),
                404
        );

        Assert.assertEquals(
                response
                        .jsonPath()
                        .getString("error"),
                "Not Found"
        );
    }
}
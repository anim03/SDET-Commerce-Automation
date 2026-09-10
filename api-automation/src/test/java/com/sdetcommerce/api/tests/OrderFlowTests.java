package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.model.AddToCartRequest;
import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class OrderFlowTests extends BaseTest {

    @Test
    public void shouldCreateOrderSuccessfully() {

        Long productId = null;
        Long orderId = null;

        try {

            Response productResponse =
                    productClient.createProduct(
                            adminToken,
                            new ProductRequest(
                                    TestDataFactory.uniqueProductName(
                                            "Order Product"
                                    ),
                                    "Order automation product",
                                    new BigDecimal("75.00"),
                                    10
                            )
                    );

            Assert.assertEquals(
                    productResponse.statusCode(),
                    201
            );

            productId =
                    productResponse
                            .jsonPath()
                            .getLong("id");

            Response cartResponse =
                    cartClient.addToCart(
                            token,
                            new AddToCartRequest(
                                    productId,
                                    2
                            )
                    );

            Assert.assertEquals(
                    cartResponse.statusCode(),
                    201
            );

            Response orderResponse =
                    orderClient.createOrder(token);

            Assert.assertEquals(
                    orderResponse.statusCode(),
                    201
            );

            orderId =
                    orderResponse
                            .jsonPath()
                            .getLong("orderId");

            Assert.assertNotNull(orderId);

            Assert.assertEquals(
                    orderResponse
                            .jsonPath()
                            .getString("status"),
                    "CREATED"
            );

        } finally {

            TestDataCleanup.clearCartSafely(
                    cartClient,
                    token
            );

            TestDataCleanup.deleteOrderIfExists(
                    orderId
            );

            TestDataCleanup.deleteProductIfExists(
                    productClient,
                    adminToken,
                    productId
            );
        }
    }
}
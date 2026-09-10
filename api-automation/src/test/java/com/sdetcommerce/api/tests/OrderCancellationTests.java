package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.model.AddToCartRequest;
import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class OrderCancellationTests extends BaseTest {

    @Test
    public void shouldCancelOrderSuccessfully() {

        Long productId = null;
        Long orderId = null;

        try {

            Response productResponse =
                    productClient.createProduct(
                            adminToken,
                            new ProductRequest(
                                    TestDataFactory.uniqueProductName(
                                            "Cancel Order Product"
                                    ),
                                    "Order cancellation automation",
                                    new BigDecimal("90.00"),
                                    10
                            )
                    );

            productId =
                    productResponse
                            .jsonPath()
                            .getLong("id");

            cartClient.addToCart(
                    token,
                    new AddToCartRequest(
                            productId,
                            1
                    )
            );

            Response orderResponse =
                    orderClient.createOrder(token);

            orderId =
                    orderResponse
                            .jsonPath()
                            .getLong("orderId");

            Response cancelResponse =
                    orderClient.cancelOrder(
                            token,
                            orderId
                    );

            Assert.assertEquals(
                    cancelResponse.statusCode(),
                    200
            );

            Assert.assertEquals(
                    cancelResponse
                            .jsonPath()
                            .getString("status"),
                    "CANCELLED"
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
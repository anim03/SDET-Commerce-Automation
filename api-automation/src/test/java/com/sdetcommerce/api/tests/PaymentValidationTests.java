package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.model.AddToCartRequest;
import com.sdetcommerce.api.model.PaymentRequest;
import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class PaymentValidationTests extends BaseTest {

    @Test
    public void shouldRejectDuplicatePayment() {

        Long productId = null;
        Long orderId = null;

        try {

            Response productResponse =
                    productClient.createProduct(
                            adminToken,
                            new ProductRequest(
                                    TestDataFactory.uniqueProductName(
                                            "Duplicate Payment"
                                    ),
                                    "Duplicate payment validation",
                                    new BigDecimal("40.00"),
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

            PaymentRequest request =
                    new PaymentRequest(
                            orderId,
                            "CARD"
                    );

            Response firstPayment =
                    paymentClient.createPayment(
                            token,
                            request
                    );

            Assert.assertEquals(
                    firstPayment.statusCode(),
                    201
            );

            Response duplicatePayment =
                    paymentClient.createPayment(
                            token,
                            request
                    );

            Assert.assertEquals(
                    duplicatePayment.statusCode(),
                    409
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

    @Test
    public void shouldRejectPaymentForCancelledOrder() {

        Long productId = null;
        Long orderId = null;

        try {

            Response productResponse =
                    productClient.createProduct(
                            adminToken,
                            new ProductRequest(
                                    TestDataFactory.uniqueProductName(
                                            "Cancelled Payment"
                                    ),
                                    "Cancelled payment validation",
                                    new BigDecimal("60.00"),
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

            orderClient.cancelOrder(
                    token,
                    orderId
            );

            Response paymentResponse =
                    paymentClient.createPayment(
                            token,
                            new PaymentRequest(
                                    orderId,
                                    "CARD"
                            )
                    );

            Assert.assertEquals(
                    paymentResponse.statusCode(),
                    409
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
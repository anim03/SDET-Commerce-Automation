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

public class PaymentFlowTests extends BaseTest {

    @Test
    public void shouldCreatePaymentSuccessfully() {

        Long productId = null;
        Long orderId = null;

        try {

            Response productResponse =
                    productClient.createProduct(
                            adminToken,
                            new ProductRequest(
                                    TestDataFactory.uniqueProductName(
                                            "Payment Product"
                                    ),
                                    "Payment automation product",
                                    new BigDecimal("50.00"),
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
                    201
            );

            Assert.assertEquals(
                    paymentResponse
                            .jsonPath()
                            .getString("status"),
                    "SUCCESS"
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
    public void shouldGetPaymentByOrderIdSuccessfully() {

        Long productId = null;
        Long orderId = null;

        try {

            Response productResponse =
                    productClient.createProduct(
                            adminToken,
                            new ProductRequest(
                                    TestDataFactory.uniqueProductName(
                                            "Get Payment Product"
                                    ),
                                    "Get payment automation",
                                    new BigDecimal("75.00"),
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
                            2
                    )
            );

            Response orderResponse =
                    orderClient.createOrder(token);

            orderId =
                    orderResponse
                            .jsonPath()
                            .getLong("orderId");

            Response paymentResponse =
                    paymentClient.createPayment(
                            token,
                            new PaymentRequest(
                                    orderId,
                                    "CARD"
                            )
                    );

            Long paymentId =
                    paymentResponse
                            .jsonPath()
                            .getLong("paymentId");

            Response getResponse =
                    paymentClient.getPaymentByOrderId(
                            token,
                            orderId
                    );

            Assert.assertEquals(
                    getResponse.statusCode(),
                    200
            );

            Assert.assertEquals(
                    getResponse
                            .jsonPath()
                            .getLong("paymentId"),
                    paymentId.longValue()
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
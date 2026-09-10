package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.database.DatabaseHelper;
import com.sdetcommerce.api.database.PaymentDbRecord;
import com.sdetcommerce.api.model.AddToCartRequest;
import com.sdetcommerce.api.model.PaymentRequest;
import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class PaymentDatabaseTests extends BaseTest {

    @Test
    public void shouldPersistPaymentCorrectlyInDatabase() {

        Long productId = null;
        Long orderId = null;

        try {

            Response productResponse =
                    productClient.createProduct(
                            adminToken,
                            new ProductRequest(
                                    TestDataFactory.uniqueProductName(
                                            "Payment DB Product"
                                    ),
                                    "Payment DB validation",
                                    new BigDecimal("125.00"),
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

            Assert.assertEquals(
                    paymentResponse.statusCode(),
                    201
            );

            PaymentDbRecord dbPayment =
                    DatabaseHelper.getPaymentByOrderId(
                            orderId
                    );

            Assert.assertNotNull(
                    dbPayment
            );

            Assert.assertEquals(
                    dbPayment.getStatus(),
                    "SUCCESS"
            );

            Assert.assertEquals(
                    dbPayment.getPaymentMethod(),
                    "CARD"
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
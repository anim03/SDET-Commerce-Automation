package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.database.DatabaseHelper;
import com.sdetcommerce.api.database.OrderDbRecord;
import com.sdetcommerce.api.database.OrderItemDbRecord;
import com.sdetcommerce.api.model.AddToCartRequest;
import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class OrderDatabaseTests extends BaseTest {

    @Test
    public void shouldPersistOrderAndOrderItemInDatabase() {

        Long productId = null;
        Long orderId = null;

        try {

            String productName =
                    TestDataFactory.uniqueProductName(
                            "Order DB Product"
                    );

            ProductRequest productRequest =
                    new ProductRequest(
                            productName,
                            "Order database validation",
                            new BigDecimal("50.00"),
                            10
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

            Response addResponse =
                    cartClient.addToCart(
                            token,
                            new AddToCartRequest(
                                    productId,
                                    2
                            )
                    );

            Assert.assertEquals(
                    addResponse.statusCode(),
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

            OrderDbRecord orderDbRecord =
                    DatabaseHelper.getOrderById(
                            orderId
                    );

            Assert.assertNotNull(
                    orderDbRecord,
                    "Order was not found in PostgreSQL"
            );

            Assert.assertEquals(
                    orderDbRecord.getId(),
                    orderId
            );

            Assert.assertEquals(
                    orderDbRecord.getStatus(),
                    "CREATED"
            );

            Assert.assertEquals(
                    orderDbRecord
                            .getTotalAmount()
                            .compareTo(
                                    new BigDecimal("100.00")
                            ),
                    0
            );

            OrderItemDbRecord itemDbRecord =
                    DatabaseHelper
                            .getOrderItemByOrderId(
                                    orderId
                            );

            Assert.assertNotNull(
                    itemDbRecord,
                    "Order item was not found in PostgreSQL"
            );

            Assert.assertEquals(
                    itemDbRecord.getOrderId(),
                    orderId
            );

            Assert.assertEquals(
                    itemDbRecord.getProductId(),
                    productId
            );

            Assert.assertEquals(
                    itemDbRecord.getProductName(),
                    productName
            );

            Assert.assertEquals(
                    itemDbRecord.getQuantity(),
                    Integer.valueOf(2)
            );

            Assert.assertEquals(
                    itemDbRecord
                            .getPrice()
                            .compareTo(
                                    new BigDecimal("50.00")
                            ),
                    0
            );

            Assert.assertEquals(
                    itemDbRecord
                            .getSubtotal()
                            .compareTo(
                                    new BigDecimal("100.00")
                            ),
                    0
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
    public void shouldPersistCancelledOrderStatusInDatabase() {

        Long productId = null;
        Long orderId = null;

        try {

            String productName =
                    TestDataFactory.uniqueProductName(
                            "Cancelled Order DB Product"
                    );

            ProductRequest productRequest =
                    new ProductRequest(
                            productName,
                            "Cancelled order DB validation",
                            new BigDecimal("35.00"),
                            10
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

            Response addResponse =
                    cartClient.addToCart(
                            token,
                            new AddToCartRequest(
                                    productId,
                                    2
                            )
                    );

            Assert.assertEquals(
                    addResponse.statusCode(),
                    201
            );

            Response createOrderResponse =
                    orderClient.createOrder(token);

            Assert.assertEquals(
                    createOrderResponse.statusCode(),
                    201
            );

            orderId =
                    createOrderResponse
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

            OrderDbRecord databaseOrder =
                    DatabaseHelper.getOrderById(
                            orderId
                    );

            Assert.assertNotNull(
                    databaseOrder
            );

            Assert.assertEquals(
                    databaseOrder.getStatus(),
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
package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.database.DatabaseHelper;
import com.sdetcommerce.api.database.ProductDbRecord;
import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class ProductDatabaseTests extends BaseTest {

    @Test
    public void shouldPersistProductCorrectlyInDatabase() {

        Long productId = null;

        try {

            String productName =
                    TestDataFactory.uniqueProductName(
                            "Product DB"
                    );

            ProductRequest request =
                    new ProductRequest(
                            productName,
                            "Database validation product",
                            new BigDecimal("299.99"),
                            25
                    );

            /*
             * Product creation is an ADMIN operation.
             */
            Response response =
                    productClient.createProduct(
                            adminToken,
                            request
                    );

            Assert.assertEquals(
                    response.statusCode(),
                    201
            );

            productId =
                    response
                            .jsonPath()
                            .getLong("id");

            Assert.assertNotNull(
                    productId,
                    "Product ID should not be null"
            );

            /*
             * Direct PostgreSQL validation.
             */
            ProductDbRecord databaseProduct =
                    DatabaseHelper.getProductById(
                            productId
                    );

            Assert.assertNotNull(
                    databaseProduct,
                    "Product should exist in PostgreSQL"
            );

            Assert.assertEquals(
                    databaseProduct.getName(),
                    productName
            );

            Assert.assertEquals(
                    databaseProduct.getDescription(),
                    "Database validation product"
            );

            Assert.assertEquals(
                    databaseProduct
                            .getPrice()
                            .compareTo(
                                    new BigDecimal("299.99")
                            ),
                    0
            );

            Assert.assertEquals(
                    databaseProduct.getStock(),
                    25
            );

        } finally {

            /*
             * Product deletion is also ADMIN operation.
             */
            TestDataCleanup.deleteProductIfExists(
                    productClient,
                    adminToken,
                    productId
            );
        }
    }
}
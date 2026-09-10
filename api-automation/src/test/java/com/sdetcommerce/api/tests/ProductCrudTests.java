package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class ProductCrudTests extends BaseTest {

    @Test
    public void shouldCreateProductSuccessfully() {

        Long productId = null;

        try {

            ProductRequest request =
                    new ProductRequest(
                            TestDataFactory.uniqueProductName(
                                    "CRUD Product"
                            ),
                            "Product CRUD automation",
                            new BigDecimal("199.99"),
                            10
                    );

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

            Assert.assertNotNull(productId);

        } finally {

            TestDataCleanup.deleteProductIfExists(
                    productClient,
                    adminToken,
                    productId
            );
        }
    }

    @Test
    public void shouldGetProductByIdSuccessfully() {

        Long productId = null;

        try {

            Response createResponse =
                    productClient.createProduct(
                            adminToken,
                            new ProductRequest(
                                    TestDataFactory.uniqueProductName(
                                            "Get Product"
                                    ),
                                    "Product GET automation",
                                    new BigDecimal("149.99"),
                                    12
                            )
                    );

            Assert.assertEquals(
                    createResponse.statusCode(),
                    201
            );

            productId =
                    createResponse
                            .jsonPath()
                            .getLong("id");

            Response getResponse =
                    productClient.getProductById(
                            token,
                            productId
                    );

            Assert.assertEquals(
                    getResponse.statusCode(),
                    200
            );

            Assert.assertEquals(
                    getResponse
                            .jsonPath()
                            .getLong("id"),
                    productId.longValue()
            );

        } finally {

            TestDataCleanup.deleteProductIfExists(
                    productClient,
                    adminToken,
                    productId
            );
        }
    }

    @Test
    public void shouldUpdateProductSuccessfully() {

        Long productId = null;

        try {

            Response createResponse =
                    productClient.createProduct(
                            adminToken,
                            new ProductRequest(
                                    TestDataFactory.uniqueProductName(
                                            "Update Product"
                                    ),
                                    "Original description",
                                    new BigDecimal("100.00"),
                                    5
                            )
                    );

            Assert.assertEquals(
                    createResponse.statusCode(),
                    201
            );

            productId =
                    createResponse
                            .jsonPath()
                            .getLong("id");

            ProductRequest updateRequest =
                    new ProductRequest(
                            TestDataFactory.uniqueProductName(
                                    "Updated Product"
                            ),
                            "Updated description",
                            new BigDecimal("250.00"),
                            20
                    );

            Response updateResponse =
                    productClient.updateProduct(
                            adminToken,
                            productId,
                            updateRequest
                    );

            Assert.assertEquals(
                    updateResponse.statusCode(),
                    200
            );

            Assert.assertEquals(
                    updateResponse
                            .jsonPath()
                            .getString("description"),
                    "Updated description"
            );

        } finally {

            TestDataCleanup.deleteProductIfExists(
                    productClient,
                    adminToken,
                    productId
            );
        }
    }

    @Test
    public void shouldDeleteProductSuccessfully() {

        Response createResponse =
                productClient.createProduct(
                        adminToken,
                        new ProductRequest(
                                TestDataFactory.uniqueProductName(
                                        "Delete Product"
                                ),
                                "Product delete automation",
                                new BigDecimal("80.00"),
                                8
                        )
                );

        Assert.assertEquals(
                createResponse.statusCode(),
                201
        );

        Long productId =
                createResponse
                        .jsonPath()
                        .getLong("id");

        Response deleteResponse =
                productClient.deleteProduct(
                        adminToken,
                        productId
                );

        Assert.assertTrue(
                deleteResponse.statusCode() == 200
                        || deleteResponse.statusCode() == 204
        );
    }
}
package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class ProductSearchTests extends BaseTest {

    @Test
    public void shouldSearchProductByPartialName() {

        Long productId = null;

        try {

            String productName =
                    TestDataFactory.uniqueProductName(
                            "Automation Search Keyboard"
                    );

            ProductRequest request =
                    new ProductRequest(
                            productName,
                            "Product created for search testing",
                            new BigDecimal("199.99"),
                            15
                    );

            Response createResponse =
                    productClient.createProduct(
                            adminToken,
                            request
                    );

            Assert.assertEquals(
                    createResponse.statusCode(),
                    201
            );

            productId =
                    createResponse
                            .jsonPath()
                            .getLong("id");

            String uniqueSearchTerm =
                    productName.substring(
                            productName.lastIndexOf("-") + 1
                    );

            Response searchResponse =
                    productClient.searchProducts(
                            token,
                            uniqueSearchTerm
                    );

            Assert.assertEquals(
                    searchResponse.statusCode(),
                    200
            );

            Assert.assertTrue(
                    searchResponse
                            .jsonPath()
                            .getList("name")
                            .contains(productName)
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
    public void shouldSearchProductIgnoringCase() {

        Long productId = null;

        try {

            String productName =
                    TestDataFactory.uniqueProductName(
                            "Case Sensitive Laptop"
                    );

            ProductRequest request =
                    new ProductRequest(
                            productName,
                            "Case insensitive search test",
                            new BigDecimal("999.99"),
                            8
                    );

            Response createResponse =
                    productClient.createProduct(
                            adminToken,
                            request
                    );

            Assert.assertEquals(
                    createResponse.statusCode(),
                    201
            );

            productId =
                    createResponse
                            .jsonPath()
                            .getLong("id");

            Response searchResponse =
                    productClient.searchProducts(
                            token,
                            productName.toUpperCase()
                    );

            Assert.assertEquals(
                    searchResponse.statusCode(),
                    200
            );

            Assert.assertTrue(
                    searchResponse
                            .jsonPath()
                            .getList("name")
                            .contains(productName)
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
    public void shouldReturnEmptyListWhenNoProductMatches() {

        String nonExistingProductName =
                TestDataFactory.uniqueProductName(
                        "NonExistingProduct"
                );

        Response response =
                productClient.searchProducts(
                        token,
                        nonExistingProductName
                );

        Assert.assertEquals(
                response.statusCode(),
                200
        );

        Assert.assertTrue(
                response
                        .jsonPath()
                        .getList("$")
                        .isEmpty()
        );
    }
}
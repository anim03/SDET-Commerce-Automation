package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class ProductRbacTests extends BaseTest {

    @Test
    public void shouldRejectProductCreationForRoleUser() {

        ProductRequest request =
                new ProductRequest(
                        TestDataFactory.uniqueProductName(
                                "RBAC User Product"
                        ),
                        "ROLE_USER must not create product",
                        new BigDecimal("100.00"),
                        10
                );

        Response response =
                productClient.createProduct(
                        token,
                        request
                );

        Assert.assertEquals(
                response.statusCode(),
                403
        );
    }

    @Test
    public void shouldAllowProductCreationForRoleAdmin() {

        Long productId = null;

        try {

            ProductRequest request =
                    new ProductRequest(
                            TestDataFactory.uniqueProductName(
                                    "RBAC Admin Product"
                            ),
                            "ROLE_ADMIN can create product",
                            new BigDecimal("150.00"),
                            20
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

            Assert.assertNotNull(
                    productId
            );

        } finally {

            TestDataCleanup.deleteProductIfExists(
                    productClient,
                    adminToken,
                    productId
            );
        }
    }
}
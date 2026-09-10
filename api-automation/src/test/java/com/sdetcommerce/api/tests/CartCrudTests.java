package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.model.AddToCartRequest;
import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class CartCrudTests extends BaseTest {

    @Test
    public void shouldAddProductToCart() {

        Long productId = null;

        try {

            Response productResponse =
                    productClient.createProduct(
                            adminToken,
                            new ProductRequest(
                                    TestDataFactory.uniqueProductName(
                                            "Cart Product"
                                    ),
                                    "Cart automation product",
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

        } finally {

            TestDataCleanup.clearCartSafely(
                    cartClient,
                    token
            );

            TestDataCleanup.deleteProductIfExists(
                    productClient,
                    adminToken,
                    productId
            );
        }
    }

    @Test
    public void shouldGetCartSuccessfully() {

        Long productId = null;

        try {

            Response productResponse =
                    productClient.createProduct(
                            adminToken,
                            new ProductRequest(
                                    TestDataFactory.uniqueProductName(
                                            "Get Cart Product"
                                    ),
                                    "Get cart automation",
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

            Response cartResponse =
                    cartClient.getCart(token);

            Assert.assertEquals(
                    cartResponse.statusCode(),
                    200
            );

        } finally {

            TestDataCleanup.clearCartSafely(
                    cartClient,
                    token
            );

            TestDataCleanup.deleteProductIfExists(
                    productClient,
                    adminToken,
                    productId
            );
        }
    }
}
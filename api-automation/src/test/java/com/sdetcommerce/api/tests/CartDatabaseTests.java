package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.database.CartDbRecord;
import com.sdetcommerce.api.database.DatabaseHelper;
import com.sdetcommerce.api.model.AddToCartRequest;
import com.sdetcommerce.api.model.ProductRequest;
import com.sdetcommerce.api.model.UpdateCartItemRequest;
import com.sdetcommerce.api.utils.TestDataCleanup;
import com.sdetcommerce.api.utils.TestDataFactory;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class CartDatabaseTests extends BaseTest {

    @Test
    public void shouldPersistCartItemInDatabase() {

        Long productId = null;

        try {

            String productName =
                    TestDataFactory.uniqueProductName(
                            "Cart DB Product"
                    );

            ProductRequest productRequest =
                    new ProductRequest(
                            productName,
                            "Cart database validation",
                            new BigDecimal("60.00"),
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
                                    3
                            )
                    );

            Assert.assertEquals(
                    addResponse.statusCode(),
                    201
            );

            Long cartItemId =
                    addResponse
                            .jsonPath()
                            .getLong("cartItemId");

            Assert.assertNotNull(cartItemId);

            CartDbRecord databaseCartItem =
                    DatabaseHelper.getCartItemById(
                            cartItemId
                    );

            Assert.assertNotNull(
                    databaseCartItem,
                    "Cart item was not found in PostgreSQL"
            );

            Assert.assertEquals(
                    databaseCartItem.getId(),
                    cartItemId
            );

            Assert.assertEquals(
                    databaseCartItem.getProductId(),
                    productId
            );

            Assert.assertEquals(
                    databaseCartItem.getQuantity(),
                    Integer.valueOf(3)
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
    public void shouldPersistUpdatedCartQuantityInDatabase() {

        Long productId = null;

        try {

            String productName =
                    TestDataFactory.uniqueProductName(
                            "Cart DB Update Product"
                    );

            ProductRequest productRequest =
                    new ProductRequest(
                            productName,
                            "Cart DB update validation",
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
                                    1
                            )
                    );

            Assert.assertEquals(
                    addResponse.statusCode(),
                    201
            );

            Long cartItemId =
                    addResponse
                            .jsonPath()
                            .getLong("cartItemId");

            Response updateResponse =
                    cartClient.updateCartItem(
                            token,
                            cartItemId,
                            new UpdateCartItemRequest(5)
                    );

            Assert.assertEquals(
                    updateResponse.statusCode(),
                    200
            );

            CartDbRecord databaseCartItem =
                    DatabaseHelper.getCartItemById(
                            cartItemId
                    );

            Assert.assertNotNull(
                    databaseCartItem,
                    "Updated cart item was not found in PostgreSQL"
            );

            Assert.assertEquals(
                    databaseCartItem.getQuantity(),
                    Integer.valueOf(5)
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
    public void shouldDeleteCartItemFromDatabase() {

        Long productId = null;

        try {

            String productName =
                    TestDataFactory.uniqueProductName(
                            "Cart DB Delete Product"
                    );

            ProductRequest productRequest =
                    new ProductRequest(
                            productName,
                            "Cart DB delete validation",
                            new BigDecimal("45.00"),
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

            Long cartItemId =
                    addResponse
                            .jsonPath()
                            .getLong("cartItemId");

            CartDbRecord beforeDelete =
                    DatabaseHelper.getCartItemById(
                            cartItemId
                    );

            Assert.assertNotNull(
                    beforeDelete,
                    "Cart item was not found before deletion"
            );

            Response deleteResponse =
                    cartClient.removeCartItem(
                            token,
                            cartItemId
                    );

            Assert.assertEquals(
                    deleteResponse.statusCode(),
                    204
            );

            CartDbRecord afterDelete =
                    DatabaseHelper.getCartItemById(
                            cartItemId
                    );

            Assert.assertNull(
                    afterDelete,
                    "Cart item still exists in PostgreSQL after DELETE API"
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
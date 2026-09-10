package com.sdetcommerce.api.utils;

import com.sdetcommerce.api.client.CartClient;
import com.sdetcommerce.api.client.ProductClient;
import com.sdetcommerce.api.database.DatabaseHelper;

import io.restassured.response.Response;

public class TestDataCleanup {

    private TestDataCleanup() {
    }

    public static void deleteProductIfExists(
            ProductClient productClient,
            String token,
            Long productId) {

        if (productClient == null
                || token == null
                || token.isBlank()
                || productId == null) {

            return;
        }

        try {

            Response response =
                    productClient.deleteProduct(
                            token,
                            productId
                    );

            int statusCode =
                    response.statusCode();

            if (statusCode == 204
                    || statusCode == 200
                    || statusCode == 404) {

                System.out.println(
                        "Product cleanup completed for productId="
                                + productId
                );

            } else {

                System.err.println(
                        "Product cleanup failed. "
                                + "productId="
                                + productId
                                + ", statusCode="
                                + statusCode
                );
            }

        } catch (Exception exception) {

            System.err.println(
                    "Product cleanup exception for productId="
                            + productId
                            + ": "
                            + exception.getMessage()
            );
        }
    }

    public static void clearCartSafely(
            CartClient cartClient,
            String token) {

        if (cartClient == null
                || token == null
                || token.isBlank()) {

            return;
        }

        try {

            Response response =
                    cartClient.clearCart(token);

            int statusCode =
                    response.statusCode();

            if (statusCode == 204) {

                System.out.println(
                        "Cart cleanup successful"
                );

            } else {

                System.err.println(
                        "Cart cleanup failed. Status code: "
                                + statusCode
                );
            }

        } catch (Exception exception) {

            System.err.println(
                    "Cart cleanup exception: "
                            + exception.getMessage()
            );
        }
    }

    public static void deleteOrderIfExists(
            Long orderId) {

        if (orderId == null) {
            return;
        }

        try {

            DatabaseHelper.deleteOrderById(
                    orderId
            );

        } catch (Exception exception) {

            System.err.println(
                    "Order cleanup exception for orderId="
                            + orderId
                            + ": "
                            + exception.getMessage()
            );
        }
    }
}
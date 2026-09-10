package com.sdetcommerce.api.client;

import com.sdetcommerce.api.config.RequestSpecFactory;
import com.sdetcommerce.api.model.AddToCartRequest;
import com.sdetcommerce.api.model.UpdateCartItemRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CartClient {

    public Response addToCart(
            String token,
            AddToCartRequest request) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .body(request)
                .when()
                .post("/api/cart/items")
                .then()
                .extract()
                .response();
    }

    public Response getCart(String token) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .when()
                .get("/api/cart")
                .then()
                .extract()
                .response();
    }

    public Response updateCartItem(
            String token,
            Long cartItemId,
            UpdateCartItemRequest request) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .body(request)
                .when()
                .put(
                        "/api/cart/items/" + cartItemId
                )
                .then()
                .extract()
                .response();
    }

    public Response removeCartItem(
            String token,
            Long cartItemId) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .when()
                .delete(
                        "/api/cart/items/" + cartItemId
                )
                .then()
                .extract()
                .response();
    }

    public Response clearCart(String token) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .when()
                .delete("/api/cart")
                .then()
                .extract()
                .response();
    }
    public Response getCartWithoutToken() {

    return given()
            .spec(
                    RequestSpecFactory.getBaseSpec()
            )
            .when()
            .get("/api/cart")
            .then()
            .extract()
            .response();
}
}
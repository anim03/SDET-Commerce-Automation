package com.sdetcommerce.api.client;

import com.sdetcommerce.api.config.RequestSpecFactory;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    public Response createOrder(String token) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .when()
                .post("/api/orders");
    }

    public Response getOrders(String token) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .when()
                .get("/api/orders");
    }

    public Response getOrderById(
            String token,
            Long orderId) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .pathParam(
                        "orderId",
                        orderId
                )
                .when()
                .get("/api/orders/{orderId}");
    }

    public Response cancelOrder(
            String token,
            Long orderId) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .pathParam(
                        "orderId",
                        orderId
                )
                .when()
                .put("/api/orders/{orderId}/cancel");
    }

    public Response getOrdersWithoutToken() {

        return given()
                .spec(
                        RequestSpecFactory.getBaseSpec()
                )
                .when()
                .get("/api/orders");
    }
    public Response createOrderWithoutToken() {

    return given()
            .spec(
                    RequestSpecFactory
                            .getBaseSpec()
            )
            .when()
            .post("/api/orders");
}
}
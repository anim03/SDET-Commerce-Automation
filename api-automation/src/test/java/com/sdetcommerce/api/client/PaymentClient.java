package com.sdetcommerce.api.client;

import com.sdetcommerce.api.config.RequestSpecFactory;
import com.sdetcommerce.api.model.PaymentRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PaymentClient {

    public Response createPayment(
            String token,
            PaymentRequest request) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .body(request)
                .when()
                .post("/api/payments");
    }

    public Response getPaymentByOrderId(
            String token,
            Long orderId) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .pathParam("orderId", orderId)
                .when()
                .get("/api/payments/order/{orderId}");
    }

    public Response createPaymentWithoutToken(
            PaymentRequest request) {

        return given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/payments");
    }

    public Response getPaymentWithoutToken(
            Long orderId) {

        return given()
                .pathParam("orderId", orderId)
                .when()
                .get("/api/payments/order/{orderId}");
    }
}
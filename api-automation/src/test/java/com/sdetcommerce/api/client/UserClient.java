package com.sdetcommerce.api.client;

import com.sdetcommerce.api.config.RequestSpecFactory;
import com.sdetcommerce.api.model.LoginRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {

    public Response login(LoginRequest request) {

        return given()
                .spec(RequestSpecFactory.getBaseSpec())
                .body(request)

                .when()
                .post("/api/users/login")

                .then()
                .extract()
                .response();
    }
}
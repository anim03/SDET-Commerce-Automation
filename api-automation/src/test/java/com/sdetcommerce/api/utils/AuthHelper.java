package com.sdetcommerce.api.utils;

import com.sdetcommerce.api.config.ApiConfig;
import com.sdetcommerce.api.config.RequestSpecFactory;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public final class AuthHelper {

    private AuthHelper() {
    }

    public static String getAuthToken() {

        return getToken(
                ApiConfig.getTestEmail(),
                ApiConfig.getTestPassword()
        );
    }

    public static String getAdminAuthToken() {

        return getToken(
                ApiConfig.getAdminEmail(),
                ApiConfig.getAdminPassword()
        );
    }

    private static String getToken(
            String email,
            String password) {

        String requestBody = """
                {
                  "email": "%s",
                  "password": "%s"
                }
                """.formatted(
                        email,
                        password
                );

        Response response =
                given()
                        .spec(
                                RequestSpecFactory
                                        .getBaseSpec()
                        )
                        .body(requestBody)
                        .when()
                        .post("/api/users/login");

        if (response.statusCode() != 200) {

            throw new RuntimeException(
                    "Login failed for user: "
                            + email
                            + ". Status: "
                            + response.statusCode()
                            + ". Response: "
                            + response.asString()
            );
        }

        String token =
                response
                        .jsonPath()
                        .getString("token");

        if (token == null
                || token.isBlank()) {

            throw new RuntimeException(
                    "JWT token was not returned for user: "
                            + email
            );
        }

        return token;
    }
}
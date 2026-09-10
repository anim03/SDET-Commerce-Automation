package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.client.UserClient;
import com.sdetcommerce.api.config.ApiConfig;
import com.sdetcommerce.api.model.LoginRequest;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests {

    @Test
    public void shouldLoginSuccessfully() {

        UserClient userClient =
                new UserClient();

        LoginRequest request =
        new LoginRequest(
                ApiConfig.getTestEmail(),
                ApiConfig.getTestPassword()
        );

        Response response =
                userClient.login(request);

        Assert.assertEquals(
                response.statusCode(),
                200
        );

        Assert.assertEquals(
                response.jsonPath().getString("email"),
                ApiConfig.getTestEmail()
        );

        String token =
                response.jsonPath().getString("token");

        Assert.assertNotNull(token);

        Assert.assertFalse(token.isBlank());
    }
}
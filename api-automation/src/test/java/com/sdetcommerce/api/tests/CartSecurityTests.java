package com.sdetcommerce.api.tests;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CartSecurityTests extends BaseTest {

    @Test
    public void shouldReturn401WhenAccessingCartWithoutToken() {

        Response response =
                cartClient.getCartWithoutToken();

        Assert.assertEquals(
                response.statusCode(),
                401
        );
    }
}
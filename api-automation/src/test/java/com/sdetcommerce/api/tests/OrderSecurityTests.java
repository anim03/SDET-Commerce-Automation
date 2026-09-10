package com.sdetcommerce.api.tests;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderSecurityTests extends BaseTest {

    @Test
    public void shouldReturn401WhenGettingOrdersWithoutToken() {

        Response response =
                orderClient.getOrdersWithoutToken();

        Assert.assertEquals(
                response.statusCode(),
                401
        );
    }

    @Test
    public void shouldReturn401WhenCreatingOrderWithoutToken() {

        Response response =
                orderClient.createOrderWithoutToken();

        Assert.assertEquals(
                response.statusCode(),
                401
        );
    }
}
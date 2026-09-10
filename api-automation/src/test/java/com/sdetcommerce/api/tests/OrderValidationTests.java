package com.sdetcommerce.api.tests;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.sdetcommerce.api.utils.TestDataCleanup;

public class OrderValidationTests extends BaseTest {

    @Test
    public void shouldRejectCreatingOrderWhenCartIsEmpty() {

        TestDataCleanup.clearCartSafely(
                cartClient,
                token
        );

        Response response =
                orderClient.createOrder(token);

        Assert.assertEquals(
                response.statusCode(),
                400
        );

        Assert.assertEquals(
                response
                        .jsonPath()
                        .getString("error"),
                "Bad Request"
        );
    }

    @Test
    public void shouldReturn404ForUnknownOrder() {

        Response response =
                orderClient.getOrderById(
                        token,
                        Long.MAX_VALUE
                );

        Assert.assertEquals(
                response.statusCode(),
                404
        );

        Assert.assertEquals(
                response
                        .jsonPath()
                        .getString("error"),
                "Not Found"
        );
    }

    @Test
    public void shouldReturn404WhenCancellingUnknownOrder() {

        Response response =
                orderClient.cancelOrder(
                        token,
                        Long.MAX_VALUE
                );

        Assert.assertEquals(
                response.statusCode(),
                404
        );

        Assert.assertEquals(
                response
                        .jsonPath()
                        .getString("error"),
                "Not Found"
        );
    }

    @Test
    public void shouldGetAllOrdersSuccessfully() {

        Response response =
                orderClient.getOrders(token);

        Assert.assertEquals(
                response.statusCode(),
                200
        );

        Assert.assertNotNull(
                response
                        .jsonPath()
                        .getList("$")
        );
    }
}
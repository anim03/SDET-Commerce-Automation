package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.model.PaymentRequest;

import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymentSecurityTests extends BaseTest {

    @Test
    public void shouldReturn401WhenCreatingPaymentWithoutToken() {

        Response response =
                paymentClient.createPaymentWithoutToken(
                        new PaymentRequest(
                                1L,
                                "CARD"
                        )
                );

        Assert.assertEquals(
                response.statusCode(),
                401
        );
    }

    @Test
    public void shouldReturn401WhenGettingPaymentWithoutToken() {

        Response response =
                paymentClient.getPaymentWithoutToken(
                        1L
                );

        Assert.assertEquals(
                response.statusCode(),
                401
        );
    }
}
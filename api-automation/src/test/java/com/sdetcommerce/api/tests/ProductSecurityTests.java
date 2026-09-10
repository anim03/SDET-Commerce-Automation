package com.sdetcommerce.api.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductSecurityTests extends BaseTest {

    @Test
    public void shouldReturn401WithoutToken() {

        Response response =
                productClient.getAllProductsWithoutToken();

        Assert.assertEquals(
                response.statusCode(),
                401
        );
    }

    @Test
    public void shouldReturn401ForInvalidJwt() {

        String invalidToken =
                "eyJhbGciOiJIUzI1NiJ9.invalid.signature";

        Response response =
                productClient.getAllProductsWithToken(
                        invalidToken
                );

        Assert.assertEquals(
                response.statusCode(),
                401
        );
    }

    @Test
    public void shouldReturn401ForMalformedJwt() {

        String malformedToken =
                "this-is-not-a-valid-jwt";

        Response response =
                productClient.getAllProductsWithToken(
                        malformedToken
                );

        Assert.assertEquals(
                response.statusCode(),
                401
        );
    }
}
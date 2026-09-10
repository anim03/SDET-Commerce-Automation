package com.sdetcommerce.api.tests;

import com.sdetcommerce.api.model.ProductRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class ProductValidationTests extends BaseTest {

    @Test
    public void shouldReturn404ForUnknownProduct() {

        Response response =
                productClient.getProductById(
                        token,
                        Long.MAX_VALUE
                );

        Assert.assertEquals(
                response.statusCode(),
                404
        );

        Assert.assertEquals(
                response.jsonPath().getString("error"),
                "Not Found"
        );
    }

    @DataProvider(name = "invalidProductData")
    public Object[][] invalidProductData() {

        return new Object[][]{

                {
                        "",
                        "Blank product name",
                        new BigDecimal("100.00"),
                        10
                },

                {
                        null,
                        "Null product name",
                        new BigDecimal("100.00"),
                        10
                },

                {
                        "Zero Price Product",
                        "Price cannot be zero",
                        new BigDecimal("0.00"),
                        10
                },

                {
                        "Negative Price Product",
                        "Price cannot be negative",
                        new BigDecimal("-10.00"),
                        10
                },

                {
                        "Negative Stock Product",
                        "Stock cannot be negative",
                        new BigDecimal("100.00"),
                        -1
                },

                {
                        "Null Price Product",
                        "Price cannot be null",
                        null,
                        10
                },

                {
                        "Null Stock Product",
                        "Stock cannot be null",
                        new BigDecimal("100.00"),
                        null
                }
        };
    }

    @Test(dataProvider = "invalidProductData")
    public void shouldRejectInvalidProduct(
            String name,
            String description,
            BigDecimal price,
            Integer stock) {

        ProductRequest request =
                new ProductRequest(
                        name,
                        description,
                        price,
                        stock
                );

        Response response =
                productClient.createProduct(
                            adminToken,
                        request
                );

        Assert.assertEquals(
                response.statusCode(),
                400
        );

        Assert.assertEquals(
                response.jsonPath().getString("error"),
                "Bad Request"
        );
    }

}
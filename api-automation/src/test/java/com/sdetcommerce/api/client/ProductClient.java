package com.sdetcommerce.api.client;

import com.sdetcommerce.api.config.RequestSpecFactory;
import com.sdetcommerce.api.model.ProductRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductClient {

    public Response createProduct(
            String token,
            ProductRequest request) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )
                .body(request)

                .when()
                .post("/api/products")

                .then()
                .extract()
                .response();
    }

    public Response getProductById(
            String token,
            Long productId) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )

                .when()
                .get("/api/products/" + productId)

                .then()
                .extract()
                .response();
    }

    public Response getAllProducts(
            String token) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )

                .when()
                .get("/api/products")

                .then()
                .extract()
                .response();
    }

    public Response deleteProduct(
            String token,
            Long productId) {

        return given()
                .spec(
                        RequestSpecFactory
                                .getAuthenticatedSpec(token)
                )

                .when()
                .delete("/api/products/" + productId)

                .then()
                .extract()
                .response();
    }
    public Response getAllProductsWithoutToken() {

    return given()
            .spec(RequestSpecFactory.getBaseSpec())

            .when()
            .get("/api/products")

            .then()
            .extract()
            .response();
}
public Response updateProduct(
        String token,
        Long productId,
        ProductRequest request) {

    return given()
            .spec(
                    RequestSpecFactory
                            .getAuthenticatedSpec(token)
            )
            .body(request)

            .when()
            .put("/api/products/" + productId)

            .then()
            .extract()
            .response();
}
public Response searchProducts(
        String token,
        String name) {

    return given()
            .spec(
                    RequestSpecFactory
                            .getAuthenticatedSpec(token)
            )
            .queryParam("name", name)

            .when()
            .get("/api/products/search")

            .then()
            .extract()
            .response();
}
public Response getAllProductsWithToken(String customToken) {

    return given()
            .spec(RequestSpecFactory.getBaseSpec())
            .header(
                    "Authorization",
                    "Bearer " + customToken
            )
            .when()
            .get("/api/products")
            .then()
            .extract()
            .response();
}
}
package com.sdetcommerce.api.config;

import com.sdetcommerce.api.filters.SanitizedAllureFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private RequestSpecFactory() {
    }

    private static SanitizedAllureFilter
    getReportingFilter() {

        return new SanitizedAllureFilter();
    }

    public static RequestSpecification getBaseSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(
                        ApiConfig.getBaseUrl()
                )
                .setContentType(
                        ContentType.JSON
                )
                .addFilter(
                        getReportingFilter()
                )
                .build();
    }

    public static RequestSpecification
    getAuthenticatedSpec(
            String token) {

        return new RequestSpecBuilder()
                .setBaseUri(
                        ApiConfig.getBaseUrl()
                )
                .setContentType(
                        ContentType.JSON
                )
                .addHeader(
                        "Authorization",
                        "Bearer " + token
                )
                .addFilter(
                        getReportingFilter()
                )
                .build();
    }
}
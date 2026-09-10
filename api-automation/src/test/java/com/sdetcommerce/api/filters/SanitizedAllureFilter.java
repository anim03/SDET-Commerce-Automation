package com.sdetcommerce.api.filters;

import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SanitizedAllureFilter implements Filter {

    private static final String REDACTED = "[REDACTED]";

    @Override
    public Response filter(
            FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext context) {

        String sanitizedRequest =
                buildSanitizedRequest(requestSpec);

        Allure.addAttachment(
                "API Request",
                "text/plain",
                sanitizedRequest
        );

        Response response =
                context.next(
                        requestSpec,
                        responseSpec
                );

        String sanitizedResponse =
                buildSanitizedResponse(response);

        Allure.addAttachment(
                "API Response",
                "text/plain",
                sanitizedResponse
        );

        return response;
    }

    private String buildSanitizedRequest(
            FilterableRequestSpecification requestSpec) {

        StringBuilder builder =
                new StringBuilder();

        builder.append(
                requestSpec.getMethod()
        );

        builder.append(" ");

        builder.append(
                sanitizeText(
                        requestSpec.getURI()
                )
        );

        builder.append("\n\n");

        builder.append("Headers\n");
        builder.append("-------\n");

        for (Header header :
                requestSpec.getHeaders()) {

            builder.append(
                    header.getName()
            );

            builder.append(": ");

            if (isSensitiveHeader(
                    header.getName())) {

                builder.append(REDACTED);

            } else {

                builder.append(
                        sanitizeText(
                                header.getValue()
                        )
                );
            }

            builder.append("\n");
        }

        Object body =
                requestSpec.getBody();

        if (body != null) {

            builder.append("\nBody\n");
            builder.append("----\n");

            builder.append(
                    sanitizeText(
                            String.valueOf(body)
                    )
            );

            builder.append("\n");
        }

        builder.append("\nCurl\n");
        builder.append("----\n");

        builder.append(
                buildSanitizedCurl(
                        requestSpec
                )
        );

        return builder.toString();
    }

    private String buildSanitizedResponse(
            Response response) {

        StringBuilder builder =
                new StringBuilder();

        builder.append("Status: ");

        builder.append(
                response.statusCode()
        );

        builder.append(" ");

        builder.append(
                response.statusLine()
        );

        builder.append("\n\n");

        builder.append("Headers\n");
        builder.append("-------\n");

        for (Header header :
                response.getHeaders()) {

            builder.append(
                    header.getName()
            );

            builder.append(": ");

            if (isSensitiveHeader(
                    header.getName())) {

                builder.append(REDACTED);

            } else {

                builder.append(
                        sanitizeText(
                                header.getValue()
                        )
                );
            }

            builder.append("\n");
        }

        String responseBody =
                response.asPrettyString();

        if (responseBody != null
                && !responseBody.isBlank()) {

            builder.append("\nBody\n");
            builder.append("----\n");

            builder.append(
                    sanitizeText(
                            responseBody
                    )
            );
        }

        return builder.toString();
    }

    private String buildSanitizedCurl(
            FilterableRequestSpecification requestSpec) {

        StringBuilder curl =
                new StringBuilder();

        curl.append("curl -X ");

        curl.append(
                requestSpec.getMethod()
        );

        curl.append(" '");

        curl.append(
                sanitizeText(
                        requestSpec.getURI()
                )
        );

        curl.append("'");

        for (Header header :
                requestSpec.getHeaders()) {

            curl.append(" -H '");

            curl.append(
                    header.getName()
            );

            curl.append(": ");

            if (isSensitiveHeader(
                    header.getName())) {

                curl.append(REDACTED);

            } else {

                curl.append(
                        sanitizeText(
                                header.getValue()
                        )
                );
            }

            curl.append("'");
        }

        Object body =
                requestSpec.getBody();

        if (body != null) {

            curl.append(" -d '");

            String sanitizedBody =
                    sanitizeText(
                            String.valueOf(body)
                    )
                            .replace(
                                    "'",
                                    "'\\''"
                            );

            curl.append(
                    sanitizedBody
            );

            curl.append("'");
        }

        return curl.toString();
    }

    private boolean isSensitiveHeader(
            String headerName) {

        if (headerName == null) {
            return false;
        }

        String normalized =
                headerName.toLowerCase();

        return normalized.equals("authorization")
                || normalized.equals("cookie")
                || normalized.equals("set-cookie")
                || normalized.equals("x-api-key")
                || normalized.equals("api-key");
    }

    private String sanitizeText(
            String text) {

        if (text == null) {
            return null;
        }

        String sanitized = text;

        /*
         * JWT / Bearer token
         */
        sanitized =
                sanitized.replaceAll(
                        "(?i)Bearer\\s+[A-Za-z0-9._~+/=-]+",
                        "Bearer " + REDACTED
                );

        /*
         * JSON password
         */
        sanitized =
                sanitized.replaceAll(
                        "(?i)(\"password\"\\s*:\\s*\")[^\"]*(\")",
                        "$1" + REDACTED + "$2"
                );

        /*
         * JSON token
         */
        sanitized =
                sanitized.replaceAll(
                        "(?i)(\"token\"\\s*:\\s*\")[^\"]*(\")",
                        "$1" + REDACTED + "$2"
                );

        /*
         * accessToken
         */
        sanitized =
                sanitized.replaceAll(
                        "(?i)(\"accessToken\"\\s*:\\s*\")[^\"]*(\")",
                        "$1" + REDACTED + "$2"
                );

        /*
         * refreshToken
         */
        sanitized =
                sanitized.replaceAll(
                        "(?i)(\"refreshToken\"\\s*:\\s*\")[^\"]*(\")",
                        "$1" + REDACTED + "$2"
                );

        /*
         * secret
         */
        sanitized =
                sanitized.replaceAll(
                        "(?i)(\"secret\"\\s*:\\s*\")[^\"]*(\")",
                        "$1" + REDACTED + "$2"
                );

        return sanitized;
    }
}
package com.sdetcommerce.api.config;

public final class ApiConfig {

    private ApiConfig() {
    }

    public static String getEnvironment() {
        return getOrDefault(
                "TEST_ENV",
                "local"
        );
    }

    public static String getBaseUrl() {

        String explicitBaseUrl =
                System.getenv("BASE_URL");

        if (explicitBaseUrl != null
                && !explicitBaseUrl.isBlank()) {

            return explicitBaseUrl;
        }

        return switch (
                getEnvironment().toLowerCase()
        ) {

            case "local" ->
                    "http://localhost:8080";

            case "qa" ->
                    getRequiredEnvironmentVariable(
                            "QA_BASE_URL"
                    );

            case "stage" ->
                    getRequiredEnvironmentVariable(
                            "STAGE_BASE_URL"
                    );

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported TEST_ENV: "
                                    + getEnvironment()
                    );
        };
    }

    public static String getTestEmail() {

        return getRequiredEnvironmentVariable(
                "TEST_EMAIL"
        );
    }

    public static String getTestPassword() {

        return getRequiredEnvironmentVariable(
                "TEST_PASSWORD"
        );
    }

    public static String getAdminEmail() {

        return getRequiredEnvironmentVariable(
                "ADMIN_EMAIL"
        );
    }

    public static String getAdminPassword() {

        return getRequiredEnvironmentVariable(
                "ADMIN_PASSWORD"
        );
    }

    private static String getRequiredEnvironmentVariable(
            String variableName) {

        String value =
                System.getenv(variableName);

        if (value == null
                || value.isBlank()) {

            throw new IllegalStateException(
                    "Required environment variable is missing: "
                            + variableName
            );
        }

        return value;
    }

    private static String getOrDefault(
            String variableName,
            String defaultValue) {

        String value =
                System.getenv(variableName);

        if (value == null
                || value.isBlank()) {

            return defaultValue;
        }

        return value;
    }
}
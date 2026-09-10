package com.sdetcommerce.api.config;

public class DatabaseConfig {

    private static final String DEFAULT_DB_URL =
            "jdbc:postgresql://localhost:5432/sdetcommerce";

    private DatabaseConfig() {
}

    public static String getDbUrl() {

        return System.getProperty(
                "dbUrl",
                DEFAULT_DB_URL
        );
    }

    public static String getDbUsername() {

        return getRequiredConfig(
                "dbUsername",
                "DB_USERNAME"
        );
    }

    public static String getDbPassword() {

        return getRequiredConfig(
                "dbPassword",
                "DB_PASSWORD"
        );
    }

    private static String getRequiredConfig(
            String systemProperty,
            String environmentVariable) {

        String value =
                System.getProperty(systemProperty);

        if (value == null || value.isBlank()) {
            value = System.getenv(environmentVariable);
        }

        if (value == null || value.isBlank()) {

            throw new IllegalStateException(
                    "Required database configuration missing. "
                            + "Provide -D"
                            + systemProperty
                            + " or environment variable "
                            + environmentVariable
            );
        }

        return value;
    }
}
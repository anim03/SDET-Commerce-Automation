package com.sdetcommerce.api.listeners;

import com.sdetcommerce.api.config.ApiConfig;
import org.testng.IExecutionListener;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AllureEnvironmentListener implements IExecutionListener {

    @Override
    public void onExecutionStart() {

        Properties properties = new Properties();

        properties.setProperty(
                "Environment",
                ApiConfig.getEnvironment()
        );

        properties.setProperty(
                "Base URL",
                ApiConfig.getBaseUrl()
        );

        properties.setProperty(
                "Framework",
                "REST Assured"
        );

        properties.setProperty(
                "Test Runner",
                "TestNG"
        );

        properties.setProperty(
                "Java Version",
                System.getProperty("java.version")
        );

        properties.setProperty(
                "Operating System",
                System.getProperty("os.name")
        );

        Path resultsDirectory =
                Path.of("target", "allure-results");

        try {

            Files.createDirectories(resultsDirectory);

            try (OutputStream output =
                         Files.newOutputStream(
                                 resultsDirectory.resolve(
                                         "environment.properties"
                                 )
                         )) {

                properties.store(
                        output,
                        "Allure Environment Information"
                );
            }

        } catch (IOException exception) {

            throw new RuntimeException(
                    "Unable to create Allure environment.properties",
                    exception
            );
        }
    }
}
package com.sdetcommerce.api.utils;

import java.util.UUID;

public class TestDataFactory {

    private TestDataFactory() {
    }

    public static String uniqueProductName(String prefix) {

        String uniqueId =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8);

        return prefix + "-" + uniqueId;
    }
}
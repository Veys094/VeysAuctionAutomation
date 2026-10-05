
package com.veysauction.config;

public final class Config {

    private Config() {}

    public static final String BASE_URL =
            System.getProperty(
                    "baseUrl",
                    "http://127.0.0.1:8000"
            );

    public static final String API_URL =
            System.getProperty(
                    "apiUrl",
                    "http://127.0.0.1:8000"
            );

    public static final boolean HEADLESS =
            Boolean.parseBoolean(
                    System.getProperty("headless", "false")
            );

    public static final int EXPLICIT_WAIT_SECONDS = 10;
}
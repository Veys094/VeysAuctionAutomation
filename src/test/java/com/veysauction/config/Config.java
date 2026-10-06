package com.veysauction.config;

public final class Config {

    private Config() {}

    private static final String ENV =
            System.getProperty("env", "local");

    public static final String BASE_URL;
    public static final String API_URL;

    static {
        switch (ENV.toLowerCase()) {

            case "qa":
                BASE_URL = "https://veysauction.onrender.com";
                API_URL = "https://veysauction.onrender.com";
                break;

            case "local":
            default:
                BASE_URL = "http://127.0.0.1:8000";
                API_URL = "http://127.0.0.1:8000";
                break;
        }
    }

    public static final boolean HEADLESS =
            Boolean.parseBoolean(
                    System.getProperty("headless", "false")
            );

    public static final int EXPLICIT_WAIT_SECONDS = 10;
}
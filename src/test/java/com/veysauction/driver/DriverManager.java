package com.veysauction.driver;

import com.veysauction.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER =
            new ThreadLocal<>();

    private DriverManager() {}

    public static WebDriver getDriver() {

        WebDriver driver = DRIVER.get();

        if (driver == null) {

            ChromeOptions options = new ChromeOptions();

            if (Config.HEADLESS) {
                options.addArguments("--headless=new");
            }

            options.addArguments("--window-size=1920,1080");

            driver = new ChromeDriver(options);

            driver.manage().timeouts()
                    .implicitlyWait(Duration.ZERO);

            driver.manage().timeouts()
                    .pageLoadTimeout(Duration.ofSeconds(30));

            DRIVER.set(driver);
        }

        return DRIVER.get();
    }

    public static void quitDriver() {

        WebDriver driver = DRIVER.get();

        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
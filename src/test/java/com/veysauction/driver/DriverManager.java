
package com.veysauction.driver;

import com.veysauction.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public final class DriverManager {

    private static WebDriver driver;

    private DriverManager() {}

    public static WebDriver getDriver() {
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
        }

        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

package com.veysauction.pages;

import com.veysauction.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By carTitle =
            By.xpath("//*[normalize-space()='Hyundai Elantra']");

    private final By currentBid =
            By.xpath("//*[normalize-space()='$15000.00']");

    private final By placeBidButton =
            By.xpath(
                    "//a[contains(normalize-space(), 'Place a bid')]"
                            + " | //button[contains(normalize-space(), 'Place a bid')]"
            );

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS)
        );
    }

    public ProductPage open(int carId) {
        driver.get(
                Config.BASE_URL + "/cars/" + carId + "/details"
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(carTitle)
        );

        return this;
    }

    public boolean isCarTitleVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(carTitle)
        ).isDisplayed();
    }

    public String getCarTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(carTitle)
        ).getText();
    }

    public boolean isCurrentBidVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(currentBid)
        ).isDisplayed();
    }

    public String getCurrentBidText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(currentBid)
        ).getText();
    }

    public boolean isPlaceBidButtonVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(placeBidButton)
        ).isDisplayed();
    }

    public boolean isPlaceBidButtonClickable() {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(placeBidButton)
        );

        return button.isDisplayed() && button.isEnabled();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
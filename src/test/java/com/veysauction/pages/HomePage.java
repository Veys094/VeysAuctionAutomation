
package com.veysauction.pages;

import com.veysauction.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageHeading =
            By.cssSelector(".section-title");

    private final By searchInput =
            By.id("carSearch");

    private final By carCards =
            By.cssSelector(".car-card");

    private final By emptyState =
            By.id("emptyState");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS)
        );
    }

    public HomePage open() {
        driver.get(Config.BASE_URL);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(pageHeading)
        );

        return this;
    }

    public void searchFor(String text) {
        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        );

        search.clear();
        search.sendKeys(text);

        wait.until(ExpectedConditions.attributeToBe(
                searchInput, "value", text
        ));
    }

    public void clearSearch() {
        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        );

        search.clear();

        // Ensure the input event updates the car list.
        search.sendKeys(" ");
        search.clear();

        wait.until(ExpectedConditions.attributeToBe(
                searchInput, "value", ""
        ));

        wait.until(driver ->
                getVisibleCarCount() == driver.findElements(carCards).size()
        );

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(emptyState)
        );
    }

    public List<String> getVisibleCarNames() {
        List<String> names = new ArrayList<>();

        for (WebElement card : driver.findElements(carCards)) {
            if (card.isDisplayed()) {
                names.add(card.getText());
            }
        }

        return names;
    }

    public boolean isCarVisible(String carName) {
        String expectedName = carName.toLowerCase(Locale.ROOT);

        for (String cardText : getVisibleCarNames()) {
            if (cardText.toLowerCase(Locale.ROOT).contains(expectedName)) {
                return true;
            }
        }

        return false;
    }

    public boolean isEmptyStateVisible() {
        List<WebElement> elements =
                driver.findElements(emptyState);

        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    public int getVisibleCarCount() {
        return getVisibleCarNames().size();
    }

    public boolean waitForCarCount(int expectedCount) {
        try {
            wait.until(driver ->
                    getVisibleCarCount() == expectedCount
            );
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isSearchInputVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput)
        ).isDisplayed();
    }
}
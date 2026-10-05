
package com.veysauction.pages;

import com.veysauction.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartHeading =
            By.xpath("//*[normalize-space()='Shopping Cart']");

    private final By emptyCartMessage =
            By.xpath("//*[normalize-space()='Your cart is empty']");

    private final By browseCarsLink =
            By.xpath("//a[normalize-space()='Browse Cars']");

    private final By cartItems =
            By.cssSelector(".cart-item");

    private final By carNames =
            By.cssSelector(".cart-item .car-info h2");

    private final By quantityInputs =
            By.cssSelector(".cart-item input[name='quantity']");

    private final By updateButtons =
            By.cssSelector(".cart-item .update-button");

    private final By removeButtons =
            By.cssSelector(".cart-item .remove-button");

    private final By itemPrices =
            By.cssSelector(".cart-item .price");

    private final By totalPrice =
            By.cssSelector(".total-price");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS)
        );
    }

    public CartPage open() {
        driver.get(Config.BASE_URL + "/cart");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartHeading)
        );

        return this;
    }

    public boolean isCartHeadingVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartHeading)
        ).isDisplayed();
    }

    public boolean isEmptyCartMessageVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(emptyCartMessage)
        ).isDisplayed();
    }

    public String getEmptyCartMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(emptyCartMessage)
        ).getText();
    }

    public boolean isBrowseCarsLinkVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(browseCarsLink)
        ).isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    public boolean isCarVisible(String carName) {
        for (WebElement element : driver.findElements(carNames)) {
            if (element.getText().trim().equalsIgnoreCase(carName.trim())) {
                return true;
            }
        }
        return false;
    }

    public String getFirstCarName() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        carNames
                )
        ).getText().trim();
    }

    public String getFirstQuantity() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        quantityInputs
                )
        ).getAttribute("value");
    }

    public void updateFirstQuantity(String quantity) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        quantityInputs
                )
        );

        input.clear();
        input.sendKeys(quantity);

        wait.until(
                ExpectedConditions.elementToBeClickable(updateButtons)
        ).click();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartHeading)
        );

        wait.until(
                ExpectedConditions.attributeToBe(
                        quantityInputs, "value", quantity
                )
        );
    }

    public void removeFirstItem() {
        int previousCount = getCartItemCount();

        if (previousCount == 0) {
            throw new IllegalStateException(
                    "Cannot remove an item: the cart is empty."
            );
        }

        wait.until(
                ExpectedConditions.elementToBeClickable(removeButtons)
        ).click();

        wait.until(
                driver -> driver.findElements(cartItems).size()
                        < previousCount
        );
    }

    public String getFirstItemPrice() {
        List<WebElement> prices = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        itemPrices
                )
        );

        return prices.get(0).getText().trim();
    }

    public String getTotalPrice() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(totalPrice)
        ).getText().trim();
    }

    public void clearCart() {
        open();

        while (getCartItemCount() > 0) {
            removeFirstItem();
        }

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        emptyCartMessage
                )
        );
    }

    public void highlightElement(By locator, String description) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );

        js.executeScript(
                "arguments[0].style.outline='3px solid red';" +
                        "arguments[0].style.outlineOffset='4px';",
                element
        );

        System.out.println("VISIBLE CHECK: " + description);

        pause(2000);

        js.executeScript(
                "arguments[0].style.outline='';" +
                        "arguments[0].style.outlineOffset='';",
                element
        );
    }

    private void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted", e);
        }
    }

    public By getCartHeadingLocator() {
        return cartHeading;
    }

    public By getEmptyCartMessageLocator() {
        return emptyCartMessage;
    }

    public By getBrowseCarsLinkLocator() {
        return browseCarsLink;
    }
}
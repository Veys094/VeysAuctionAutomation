package ui;

import com.veysauction.base.BaseTest;
import com.veysauction.pages.CartPage;
import com.veysauction.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class CartPageTest extends BaseTest {

    // Pause so the browser actions are easy to follow.
    private void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test pause interrupted", e);
        }
    }

    private void log(String message) {
        System.out.println("\n>>> " + message);
    }

    /**
     * Adds the first available car to the cart.
     */
    private CartPage prepareCartWithFirstCar() {
        log("STEP 1: Open the shopping cart and clean old items.");

        CartPage cartPage = new CartPage(driver);
        cartPage.clearCart();
        pause(1);

        log("STEP 2: Open the home page.");
        HomePage homePage = new HomePage(driver);
        homePage.open();
        pause(2);

        log("STEP 3: Find the Add to Cart button.");

        List<WebElement> buttons =
                driver.findElements(
                        By.cssSelector(".car-card .add-cart")
                );

        Assert.assertFalse(
                buttons.isEmpty(),
                "No Add to Cart buttons were found."
        );

        WebElement addButton = buttons.get(0);

        log("STEP 4: Scroll to the button.");
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                addButton
        );
        pause(1);

        log("STEP 5: Add the car to the cart.");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(addButton));

        addButton.click();
        pause(2);

        log("STEP 6: Open the shopping cart.");
        cartPage.open();
        pause(2);

        Assert.assertTrue(
                cartPage.getCartItemCount() > 0,
                "The car was not added to the cart."
        );

        log("PASS: The car was added successfully.");
        return cartPage;
    }

    private BigDecimal parsePrice(String text) {
        return new BigDecimal(
                text.replace("$", "")
                        .replace(",", "")
                        .trim()
        );
    }

    // TEST 1: Verify that an empty cart displays the correct message.
    @Test(groups = {"smoke", "regression"},
            priority = 1)
    public void emptyCartShouldDisplayCorrectMessage() {

        log("TEST 1: VERIFY EMPTY SHOPPING CART");

        CartPage cartPage = new CartPage(driver);

        log("Opening the shopping cart.");
        cartPage.clearCart();
        pause(2);

        log("Checking the cart heading.");
        Assert.assertTrue(
                cartPage.isCartHeadingVisible(),
                "Shopping Cart heading is missing."
        );
        pause(1);

        log("Checking the empty-cart message.");
        Assert.assertTrue(
                cartPage.isEmptyCartMessageVisible(),
                "Empty-cart message is missing."
        );
        pause(1);

        log("Checking the Browse Cars link.");
        Assert.assertTrue(
                cartPage.isBrowseCarsLinkVisible(),
                "Browse Cars link is missing."
        );

        log("PASS: The empty cart is displayed correctly.");

        log("Keeping the browser open for 5 seconds.");
        pause(3);
    }

    // TEST 2: Verify that an added car appears in the cart.
    @Test(groups = {"smoke", "regression"},
            priority = 2)
    public void addedCarShouldBeVisibleInCart() {

        log("TEST 2: VERIFY ADDED CAR");

        CartPage cartPage = prepareCartWithFirstCar();

        String carName = cartPage.getFirstCarName();

        log("Checking the car name: " + carName);
        Assert.assertFalse(
                carName == null || carName.isBlank(),
                "Car name is empty."
        );

        Assert.assertTrue(
                cartPage.isCarVisible(carName),
                "Added car is not visible in the cart."
        );

        Assert.assertEquals(
                cartPage.getCartItemCount(),
                1,
                "Expected one car in the cart."
        );

        log("PASS: " + carName + " is visible in the cart.");
        pause(3);
    }

    // TEST 3: Verify that the car quantity can be updated.
    @Test(groups = {"regression"},
            priority = 3)
    public void quantityShouldBeUpdated() {

        log("TEST 3: UPDATE CAR QUANTITY");

        CartPage cartPage = prepareCartWithFirstCar();

        log("Checking the initial quantity.");
        Assert.assertEquals(
                cartPage.getFirstQuantity(),
                "1",
                "Initial quantity should be 1."
        );
        pause(1);

        log("Changing the quantity from 1 to 2.");
        cartPage.updateFirstQuantity("2");
        pause(2);

        log("Checking the updated quantity.");
        Assert.assertEquals(
                cartPage.getFirstQuantity(),
                "2",
                "Quantity was not updated to 2."
        );

        log("PASS: Quantity was updated successfully.");
        pause(5);
    }

    // TEST 4: Verify that removing the car empties the cart.
    @Test(groups = {"regression"},
            priority = 4)
    public void removingCarShouldEmptyCart() {

        log("TEST 4: REMOVE CAR FROM CART");

        CartPage cartPage = prepareCartWithFirstCar();

        log("Checking that the cart contains one item.");
        Assert.assertEquals(
                cartPage.getCartItemCount(),
                1,
                "Expected one item before removal."
        );
        pause(1);

        log("Clicking Remove.");
        cartPage.removeFirstItem();
        pause(2);

        log("Checking that the cart is empty.");
        Assert.assertEquals(
                cartPage.getCartItemCount(),
                0,
                "Cart should contain no items after removal."
        );

        Assert.assertTrue(
                cartPage.isEmptyCartMessageVisible(),
                "Empty-cart message should appear after removal."
        );

        log("PASS: The car was removed successfully.");
        pause(3);
    }

    // TEST 5: Verify that the total changes with the quantity.
    @Test(groups = {"regression"},
            priority = 5)
    public void totalShouldMatchPriceAndQuantity() {

        log("TEST 5: VERIFY CART TOTAL");

        CartPage cartPage = prepareCartWithFirstCar();

        log("Reading the car price.");
        BigDecimal price = parsePrice(
                cartPage.getFirstItemPrice()
        );

        log("Price per car: $" + price);
        pause(1);

        log("Changing the quantity to 2.");
        cartPage.updateFirstQuantity("2");
        pause(2);

        BigDecimal expectedTotal =
                price.multiply(new BigDecimal("2"))
                        .setScale(2);

        log("Expected total: $" + expectedTotal);

        BigDecimal actualTotal = parsePrice(
                cartPage.getTotalPrice()
        );

        log("Actual total: $" + actualTotal);

        Assert.assertEquals(
                actualTotal.compareTo(expectedTotal),
                0,
                "Cart total does not match price multiplied by quantity."
        );

        log("PASS: The cart total is correct.");
        pause(3);
    }
}

package ui;

import com.veysauction.base.BaseTest;
import com.veysauction.pages.ProductPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ProductPageTest extends BaseTest {

    private void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted", e);
        }
    }

    private void showElement(WebElement element, String message) {
        System.out.println("\n>>> " + message);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Scroll the element into the center of the browser.
        js.executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );

        pause(1000);

        // Highlight the element so it is easy to see.
        js.executeScript(
                "arguments[0].style.outline='3px solid red';" +
                        "arguments[0].style.outlineOffset='4px';",
                element
        );

        pause(2500);

        // Remove the highlight.
        js.executeScript(
                "arguments[0].style.outline='';" +
                        "arguments[0].style.outlineOffset='';",
                element
        );

        pause(1000);
    }

    @Test(groups = {"smoke", "regression"})
    public void productPageShouldDisplayCarTitle() {

        System.out.println("\n========== TEST 1: CAR TITLE ==========");

        ProductPage productPage = new ProductPage(driver);

        System.out.println("STEP 1: Open the Hyundai Elantra page.");
        productPage.open(3);
        pause(2500);

        System.out.println("STEP 2: Find and highlight the car title.");

        WebElement title = driver.findElement(
                By.xpath("//*[normalize-space()='Hyundai Elantra']")
        );

        showElement(title, "Checking the car title");

        System.out.println("STEP 3: Verify the title text.");
        pause(1000);

        assertTrue(
                productPage.isCarTitleVisible(),
                "FAIL: Car title is not visible."
        );

        assertEquals(
                productPage.getCarTitle(),
                "Hyundai Elantra",
                "FAIL: The car title is incorrect."
        );

        System.out.println("PASS: Car title is visible and correct.");
        pause(2500);
    }

    @Test(groups = {"regression"})
    public void productPageShouldDisplayCurrentBid() {

        System.out.println("\n========== TEST 2: CURRENT BID ==========");

        ProductPage productPage = new ProductPage(driver);

        System.out.println("STEP 1: Open the Hyundai Elantra page.");
        productPage.open(3);
        pause(2500);

        System.out.println("STEP 2: Find and highlight the current bid.");

        WebElement bid = driver.findElement(
                By.xpath("//*[normalize-space()='$15000.00']")
        );

        showElement(bid, "Checking the current bid");

        System.out.println("STEP 3: Verify the current bid is visible.");
        pause(1000);

        assertTrue(
                productPage.isCurrentBidVisible(),
                "FAIL: Current bid is not visible."
        );

        System.out.println(
                "PASS: Current bid is visible: "
                        + productPage.getCurrentBidText()
        );

        pause(2500);
    }

    @Test(groups = {"regression"})
    public void placeBidButtonShouldBeVisibleAndClickable() {

        System.out.println("\n========== TEST 3: PLACE A BID BUTTON ==========");

        ProductPage productPage = new ProductPage(driver);

        System.out.println("STEP 1: Open the Hyundai Elantra page.");
        productPage.open(3);
        pause(2500);

        System.out.println("STEP 2: Find and highlight the Place a Bid button.");

        WebElement button = driver.findElement(
                By.xpath(
                        "//a[contains(normalize-space(), 'Place a bid')]" +
                                " | //button[contains(normalize-space(), 'Place a bid')]"
                )
        );

        showElement(button, "Checking the Place a Bid button");

        System.out.println("STEP 3: Verify the button is visible.");
        pause(1000);

        assertTrue(
                productPage.isPlaceBidButtonVisible(),
                "FAIL: Place a Bid button is not visible."
        );

        System.out.println("STEP 4: Verify the button is clickable.");
        pause(1000);

        assertTrue(
                productPage.isPlaceBidButtonClickable(),
                "FAIL: Place a Bid button is not clickable."
        );

        System.out.println(
                "PASS: Place a Bid button is visible and clickable."
        );

        pause(3000);
    }
}
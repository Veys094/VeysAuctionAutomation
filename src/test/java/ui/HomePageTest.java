
package ui;

import com.veysauction.base.BaseTest;
import com.veysauction.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class HomePageTest extends BaseTest {

    private void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted", e);
        }
    }

    private void showElement(WebElement element, String message) {
        System.out.println("VISIBLE CHECK: " + message);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );

        pause(1000);

        js.executeScript(
                "arguments[0].style.outline='3px solid red';" +
                        "arguments[0].style.outlineOffset='4px';",
                element
        );

        pause(2500);

        js.executeScript(
                "arguments[0].style.outline='';" +
                        "arguments[0].style.outlineOffset='';",
                element
        );

        pause(1000);
    }

    @Test(groups = {"smoke", "regression"})
    public void searchShouldFilterCarsAndRestoreList() {

        System.out.println("\n======================================");
        System.out.println("TEST: SEARCH AND FILTER CARS");
        System.out.println("======================================");

        HomePage homePage = new HomePage(driver);

        // STEP 1: Open the home page.
        System.out.println("STEP 1: Open the home page.");

        homePage.open();
        pause(2500);

        assertTrue(
                homePage.isSearchInputVisible(),
                "FAIL: Search input is not visible."
        );

        int initialCarCount = homePage.getVisibleCarCount();

        assertTrue(
                initialCarCount > 0,
                "Precondition failed: no cars are displayed."
        );

        System.out.println(
                "Initial number of cars: " + initialCarCount
        );

        WebElement searchInput = driver.findElement(
                By.id("carSearch")
        );

        showElement(searchInput, "Search input");

        WebElement firstCar = driver.findElement(
                By.cssSelector(".car-card")
        );

        showElement(firstCar, "First car in the list");

        // STEP 2: Search for Hyundai.
        System.out.println("\nSTEP 2: Search for Hyundai.");

        homePage.searchFor("Hyundai");
        pause(2000);

        assertTrue(
                homePage.waitForCarCount(1),
                "Expected exactly one Hyundai car."
        );

        assertTrue(
                homePage.isCarVisible("Hyundai"),
                "Hyundai should be visible in search results."
        );

        assertFalse(
                homePage.isCarVisible("BMW"),
                "BMW should not appear in Hyundai search results."
        );

        WebElement hyundai = driver.findElement(
                By.cssSelector(".car-card")
        );

        showElement(hyundai, "Hyundai search result");

        System.out.println(
                "PASS: Hyundai is displayed; BMW is filtered out."
        );

        pause(2000);

        // STEP 3: Search for a non-existing car.
        System.out.println(
                "\nSTEP 3: Search for a non-existing car."
        );

        homePage.searchFor("NonExistingCar123");
        pause(2000);

        assertTrue(
                homePage.waitForCarCount(0),
                "No cars should be displayed for an unmatched search."
        );

        assertTrue(
                homePage.isEmptyStateVisible(),
                "Empty-state message should be displayed."
        );

        WebElement emptyMessage = driver.findElement(
                By.id("emptyState")
        );

        showElement(
                emptyMessage,
                "No results message"
        );

        System.out.println(
                "PASS: No results message is displayed."
        );

        pause(2000);

        // STEP 4: Clear the search.
        System.out.println("\nSTEP 4: Clear the search.");

        showElement(
                driver.findElement(By.id("carSearch")),
                "Search input before clearing"
        );

        homePage.clearSearch();
        pause(2500);

        // STEP 5: Verify that the car list is restored.
        System.out.println(
                "\nSTEP 5: Verify that the car list is restored."
        );

        assertTrue(
                homePage.waitForCarCount(initialCarCount),
                "Clearing search should restore all cars."
        );

        assertTrue(
                homePage.isCarVisible("Hyundai"),
                "Hyundai should be visible after clearing search."
        );

        assertFalse(
                homePage.isEmptyStateVisible(),
                "Empty-state message should disappear after clearing search."
        );

        WebElement restoredCar = driver.findElement(
                By.cssSelector(".car-card")
        );

        showElement(
                restoredCar,
                "Restored car list"
        );

        System.out.println(
                "PASS: All cars are restored after clearing search."
        );

        System.out.println("\n======================================");
        System.out.println("TEST RESULT: PASS");
        System.out.println("======================================");

        pause(3000);
    }
}
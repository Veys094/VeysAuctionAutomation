
package com.veysauction.tests.api;

import com.veysauction.api.ApiClient;
import com.veysauction.base.BaseTest;
import com.veysauction.pages.HomePage;
import io.restassured.response.Response;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class CarApiUiIntegrationTest extends BaseTest {

    private static final String MY_CAR = "Elantra";

    private void pause() {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private void step(String message) {
        System.out.println("\n========== " + message + " ==========");
        pause();
    }

    private void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth',block:'center'});",
                element
        );
        pause();
    }

    @Test(groups = {"smoke", "regression"})
    public void myHyundaiElantraShouldBeVisibleInUI() {

        // STEP 1: Get cars from API.
        step("STEP 1: Getting cars from API");

        Response response = ApiClient.request()
                .when()
                .get("/cars");

        Assert.assertEquals(
                response.statusCode(),
                200,
                "API should return HTTP 200"
        );

        List<Map<String, Object>> cars =
                response.jsonPath().getList("$");

        Assert.assertNotNull(cars, "API car list must not be null");
        Assert.assertFalse(cars.isEmpty(), "API car list must not be empty");

        System.out.println("Cars received from API: " + cars.size());

        // STEP 2: Find MY Hyundai Elantra in API data.
        step("STEP 2: Looking for my Hyundai Elantra in API");

        String myCarModel = null;

        for (Map<String, Object> car : cars) {
            Object value = car.get("model");

            if (value == null) {
                continue;
            }

            String model = value.toString().trim();

            if (model.toLowerCase().contains(MY_CAR.toLowerCase())) {
                myCarModel = model;
                break;
            }
        }

        Assert.assertNotNull(
                myCarModel,
                "Hyundai Elantra was not found in API data. "
                        + "Check the model field returned by GET /cars."
        );

        System.out.println("MY CAR FOUND IN API: " + myCarModel);

        // STEP 3: Open VeysAuction.
        step("STEP 3: Opening VeysAuction homepage");

        HomePage homePage = new HomePage(driver);
        homePage.open();

        System.out.println("Website: " + driver.getCurrentUrl());

        // STEP 4: Find Elantra on the homepage.
        step("STEP 4: Finding my Elantra on the homepage");

        Assert.assertTrue(
                homePage.isCarVisible(myCarModel),
                "My Elantra from API should be visible on the homepage"
        );

        // STEP 5: Scroll down and back up.
        step("STEP 5: Scrolling through the homepage");

        JavascriptExecutor js = (JavascriptExecutor) driver;

        long pageHeight = ((Number) js.executeScript(
                "return document.body.scrollHeight"
        )).longValue();

        long viewportHeight = ((Number) js.executeScript(
                "return window.innerHeight"
        )).longValue();

        for (long position = 0;
             position < pageHeight;
             position += Math.max(viewportHeight / 2, 200)) {

            js.executeScript(
                    "window.scrollTo({top: arguments[0], behavior:'smooth'});",
                    position
            );
            pause();
        }

        js.executeScript(
                "window.scrollTo({top: 0, behavior:'smooth'});"
        );
        pause();

        // STEP 6: Scroll to the search field.
        step("STEP 6: Finding the search field");

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );

        WebElement searchInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("carSearch")
                )
        );

        scrollTo(searchInput);

        // STEP 7: Search for MY car.
        step("STEP 7: Searching for MY Hyundai Elantra");

        homePage.searchFor(myCarModel);
        pause();

        // STEP 8: Scroll to the Elantra card.
        step("STEP 8: Showing my Elantra");

        List<WebElement> carCards = driver.findElements(
                By.cssSelector(".car-card")
        );

        boolean found = false;

        for (WebElement card : carCards) {
            if (card.isDisplayed()
                    && card.getText().toLowerCase()
                    .contains(myCarModel.toLowerCase())) {

                scrollTo(card);
                found = true;
                break;
            }
        }

        Assert.assertTrue(
                found,
                "My Hyundai Elantra should appear in search results"
        );

        // STEP 9: Finish.
        step("STEP 9: MY CAR TEST PASSED!");

        System.out.println("========================================");
        System.out.println("MY CAR: HYUNDAI ELANTRA");
        System.out.println("API and UI data match.");
        System.out.println("Search result verified successfully.");
        System.out.println("========================================");

        pause();
        pause();
        pause();
        pause();
        pause();
        pause();
        pause();
        pause();
    }
}
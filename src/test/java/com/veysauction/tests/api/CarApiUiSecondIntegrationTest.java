package com.veysauction.tests.api;

import com.veysauction.api.ApiClient;
import com.veysauction.base.BaseTest;
import com.veysauction.pages.HomePage;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class CarApiUiSecondIntegrationTest extends BaseTest {

    private static final String TARGET_CAR = "Elantra";

    @Test
    public void elantraPriceFromApiShouldMatchUi() {

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

        Assert.assertNotNull(cars);
        Assert.assertFalse(cars.isEmpty());

        Map<String, Object> elantra = null;

        for (Map<String, Object> car : cars) {

            Object model = car.get("model");

            if (model != null
                    && model.toString()
                    .toLowerCase()
                    .contains(TARGET_CAR.toLowerCase())) {

                elantra = car;
                break;
            }
        }

        Assert.assertNotNull(
                elantra,
                "Elantra was not found in API"
        );

        Object apiPrice = elantra.get("price");

        Assert.assertNotNull(
                apiPrice,
                "Elantra price was not found in API"
        );

        String expectedPrice =
                apiPrice.toString().trim();

        System.out.println(
                "Elantra price from API: " + expectedPrice
        );

        HomePage homePage = new HomePage(driver);

        homePage.open();

        List<WebElement> cards =
                driver.findElements(
                        By.cssSelector(".car-card")
                );

        WebElement elantraCard = null;

        for (WebElement card : cards) {

            if (card.isDisplayed()
                    && card.getText()
                    .toLowerCase()
                    .contains(TARGET_CAR.toLowerCase())) {

                elantraCard = card;
                break;
            }
        }

        Assert.assertNotNull(
                elantraCard,
                "Elantra should be visible in UI"
        );

        String uiText =
                elantraCard.getText();

        System.out.println(
                "Elantra data from UI: " + uiText
        );

        Assert.assertTrue(
                uiText.contains(expectedPrice),
                "Elantra price from API should match UI. "
                        + "Expected: " + expectedPrice
        );
    }
}
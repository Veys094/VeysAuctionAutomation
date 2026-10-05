
package com.veysauction.tests.api;

import com.veysauction.api.ApiClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CarPatchTest {

    @Test(groups = {"regression"})
    public void patchShouldUpdateOnlyPrice() {

        String uniquePlate = "QA-" + System.currentTimeMillis();
        Integer carId = null;

        try {
            // 1. Create a test car
            Map<String, Object> newCar = Map.of(
                    "brand", "QA Test",
                    "model", "PATCH Test Car",
                    "year", 2024,
                    "price", 10000,
                    "plate_number", uniquePlate
            );

            Response createResponse = ApiClient.request()
                    .contentType(ContentType.JSON)
                    .body(newCar)
                    .when()
                    .post("/cars");

            createResponse.then()
                    .statusCode(200)
                    .contentType(ContentType.JSON);

            carId = createResponse.jsonPath().getInt("id");

            assertTrue(
                    carId > 0,
                    "Created car should have a valid ID"
            );

            // 2. PATCH: update only the price
            Response patchResponse = ApiClient.request()
                    .contentType(ContentType.JSON)
                    .pathParam("car_id", carId)
                    .body(Map.of("price", 9500))
                    .when()
                    .patch("/cars/{car_id}");

            patchResponse.then()
                    .statusCode(200)
                    .contentType(ContentType.JSON);

            // 3. Verify updated price
            assertEquals(
                    patchResponse.jsonPath().getDouble("price"),
                    9500.0,
                    0.01,
                    "Price should be updated"
            );

            // Verify other fields remain unchanged
            assertEquals(
                    patchResponse.jsonPath().getString("brand"),
                    "QA Test",
                    "Brand should remain unchanged"
            );

            assertEquals(
                    patchResponse.jsonPath().getString("model"),
                    "PATCH Test Car",
                    "Model should remain unchanged"
            );

            assertEquals(
                    patchResponse.jsonPath().getInt("year"),
                    2024,
                    "Year should remain unchanged"
            );

            assertEquals(
                    patchResponse.jsonPath().getString("plate_number"),
                    uniquePlate,
                    "Plate number should remain unchanged"
            );

            // 4. GET: verify the changes were saved
            Response getResponse = ApiClient.request()
                    .pathParam("car_id", carId)
                    .when()
                    .get("/cars/{car_id}");

            getResponse.then()
                    .statusCode(200)
                    .contentType(ContentType.JSON);

            assertEquals(
                    getResponse.jsonPath().getDouble("price"),
                    9500.0,
                    0.01,
                    "Updated price should persist after PATCH"
            );

            assertEquals(
                    getResponse.jsonPath().getString("brand"),
                    "QA Test",
                    "Brand should remain unchanged after PATCH"
            );

            System.out.println(
                    "PASS: PATCH updated only the price. ID = " + carId
            );

        } finally {
            // Clean up the test data
            if (carId != null) {
                ApiClient.request()
                        .pathParam("car_id", carId)
                        .when()
                        .delete("/cars/{car_id}");
            }
        }
    }
}
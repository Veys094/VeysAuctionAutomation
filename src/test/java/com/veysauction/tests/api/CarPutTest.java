
package com.veysauction.tests.api;

import com.veysauction.api.ApiClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CarPutTest {

    @Test(groups = {"regression"})
    public void putShouldUpdateCarDetails() {

        String uniquePlate = "QA-" + System.currentTimeMillis();
        Integer carId = null;

        try {
            // 1. Create a test car
            Map<String, Object> initialCar = Map.of(
                    "brand", "QA Original",
                    "model", "Original Model",
                    "year", 2022,
                    "price", 12000,
                    "plate_number", uniquePlate
            );

            Response createResponse = ApiClient.request()
                    .contentType(ContentType.JSON)
                    .body(initialCar)
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

            // 2. Update all car fields using PUT
            Map<String, Object> updatedCar = Map.of(
                    "brand", "QA Updated",
                    "model", "Updated Model",
                    "year", 2025,
                    "price", 15000,
                    "plate_number", uniquePlate
            );

            Response putResponse = ApiClient.request()
                    .contentType(ContentType.JSON)
                    .pathParam("car_id", carId)
                    .body(updatedCar)
                    .when()
                    .put("/cars/{car_id}");

            putResponse.then()
                    .statusCode(200)
                    .contentType(ContentType.JSON);

            // 3. Verify updated values in PUT response
            assertEquals(
                    putResponse.jsonPath().getString("brand"),
                    "QA Updated"
            );

            assertEquals(
                    putResponse.jsonPath().getString("model"),
                    "Updated Model"
            );

            assertEquals(
                    putResponse.jsonPath().getInt("year"),
                    2025
            );

            assertEquals(
                    putResponse.jsonPath().getDouble("price"),
                    15000.0,
                    0.01
            );

            assertEquals(
                    putResponse.jsonPath().getString("plate_number"),
                    uniquePlate
            );

            // 4. GET: confirm changes were saved
            Response getResponse = ApiClient.request()
                    .pathParam("car_id", carId)
                    .when()
                    .get("/cars/{car_id}");

            getResponse.then()
                    .statusCode(200)
                    .contentType(ContentType.JSON);

            assertEquals(
                    getResponse.jsonPath().getString("brand"),
                    "QA Updated"
            );

            assertEquals(
                    getResponse.jsonPath().getString("model"),
                    "Updated Model"
            );

            assertEquals(
                    getResponse.jsonPath().getInt("year"),
                    2025
            );

            assertEquals(
                    getResponse.jsonPath().getDouble("price"),
                    15000.0,
                    0.01
            );

            assertEquals(
                    getResponse.jsonPath().getString("plate_number"),
                    uniquePlate
            );

            System.out.println(
                    "PASS: PUT updated car successfully. ID = " + carId
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
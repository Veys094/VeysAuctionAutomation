
package com.veysauction.tests.api;

import com.veysauction.api.ApiClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class CarApiCrudTest {

    @Test(groups = {"smoke", "regression"})
    public void createGetAndDeleteCarShouldWork() {

        String uniquePlate = "QA-" + System.currentTimeMillis();
        Integer carId = null;
        boolean deleted = false;

        try {
            // 1. POST: create a test car
            Map<String, Object> carData = Map.of(
                    "brand", "QA Test",
                    "model", "Automation Car",
                    "year", 2024,
                    "price", 10000,
                    "plate_number", uniquePlate
            );

            Response createResponse = ApiClient.request()
                    .contentType(ContentType.JSON)
                    .body(carData)
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

            assertEquals(
                    createResponse.jsonPath().getString("brand"),
                    "QA Test"
            );

            // 2. GET: verify the created car
            Response getResponse = ApiClient.request()
                    .pathParam("car_id", carId)
                    .when()
                    .get("/cars/{car_id}");

            getResponse.then()
                    .statusCode(200)
                    .contentType(ContentType.JSON);

            assertEquals(
                    getResponse.jsonPath().getString("model"),
                    "Automation Car"
            );

            assertEquals(
                    getResponse.jsonPath().getString("plate_number"),
                    uniquePlate
            );

            // Check response header
            assertNotNull(
                    getResponse.getHeader("Content-Type"),
                    "Content-Type header should exist"
            );

            // Check response time
            assertTrue(
                    getResponse.getTime() < 10000,
                    "GET response should take less than 10 seconds"
            );

            // 3. DELETE: remove the test car
            Response deleteResponse = ApiClient.request()
                    .pathParam("car_id", carId)
                    .when()
                    .delete("/cars/{car_id}");

            deleteResponse.then().statusCode(200);
            deleted = true;

            // 4. GET: verify the car was deleted
            ApiClient.request()
                    .pathParam("car_id", carId)
                    .when()
                    .get("/cars/{car_id}")
                    .then()
                    .statusCode(404);

            System.out.println(
                    "PASS: Car created, retrieved and deleted. ID = " + carId
            );

        } finally {
            // Cleanup only if the car has not been deleted yet
            if (carId != null && !deleted) {
                ApiClient.request()
                        .pathParam("car_id", carId)
                        .when()
                        .delete("/cars/{car_id}");
            }
        }
    }
}
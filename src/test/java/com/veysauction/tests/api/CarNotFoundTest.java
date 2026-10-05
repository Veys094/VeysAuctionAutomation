package com.veysauction.tests.api;

import com.veysauction.api.ApiClient;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class CarNotFoundTest {

    @Test(groups = {"smoke", "regression"})
    public void getNonExistingCarShouldReturn404() {

        int nonExistingCarId = 999999999;

        ApiClient.request()
                .pathParam("car_id", nonExistingCarId)
                .when()
                .get("/cars/{car_id}")
                .then()
                .statusCode(404)
                .body("detail", equalTo("Car not found"));

    }
}
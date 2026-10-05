package com.veysauction.tests.api;

import com.veysauction.api.ApiClient;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class CarCrudApiTest {

    @Test(groups = {"regression"})
    public void deleteNonExistingCarShouldReturn404() {

        int nonExistingCarId = 999999999;

        ApiClient.request()
                .pathParam("car_id", nonExistingCarId)
                .when()
                .delete("/cars/{car_id}")
                .then()
                .statusCode(404)
                .body("detail", equalTo("Car not found"));
    }
}
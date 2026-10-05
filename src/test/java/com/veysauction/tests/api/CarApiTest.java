
package com.veysauction.tests.api;

import com.veysauction.api.ApiClient;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class CarApiTest {

    @Test(groups = {"smoke", "regression"})
    public void getAllCarsShouldReturn200() {

        ApiClient.request()
                .when()
                .get("/cars")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", not(empty()));
    }

    @Test(groups = {"regression"})
    public void getAllCarsShouldContainRequiredFields() {

        ApiClient.request()
                .when()
                .get("/cars")
                .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("$", everyItem(hasKey("id")))
                .body("$", everyItem(hasKey("brand")))
                .body("$", everyItem(hasKey("model")))
                .body("$", everyItem(hasKey("year")))
                .body("$", everyItem(hasKey("price")))
                .body("$", everyItem(hasKey("plate_number")));
    }
}
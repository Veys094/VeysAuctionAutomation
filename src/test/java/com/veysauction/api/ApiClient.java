
package com.veysauction.api;

import com.veysauction.config.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class ApiClient {

    private static final RequestSpecification REQUEST_SPEC =
            new RequestSpecBuilder()
                    .setBaseUri(Config.API_URL)
                    .setContentType(ContentType.JSON)
                    .setAccept(ContentType.JSON)
                    .build();

    private ApiClient() {
    }

    public static RequestSpecification request() {
        return io.restassured.RestAssured
                .given()
                .spec(REQUEST_SPEC);
    }
}
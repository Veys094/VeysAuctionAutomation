package com.veysauction.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class JsonTestDataUtil {

    private JsonTestDataUtil() {
    }

    public static Object[][] readLoginData(String resourcePath) {

        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream =
                    JsonTestDataUtil.class
                            .getClassLoader()
                            .getResourceAsStream(resourcePath);

            if (inputStream == null) {
                throw new RuntimeException(
                        "JSON file not found: " + resourcePath
                );
            }

            JsonNode root = mapper.readTree(inputStream);

            List<Object[]> data = new ArrayList<>();

            for (JsonNode node : root) {

                String username =
                        node.get("username").asText();

                String password =
                        node.get("password").asText();

                boolean expectedLogin =
                        node.get("expectedLogin").asBoolean();

                data.add(new Object[]{
                        username,
                        password,
                        expectedLogin
                });
            }

            return data.toArray(new Object[0][]);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to read JSON test data",
                    e
            );
        }
    }
}
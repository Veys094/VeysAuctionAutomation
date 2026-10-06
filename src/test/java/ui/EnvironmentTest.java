package ui;

import com.veysauction.config.Config;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EnvironmentTest {

    @Test(groups = {"smoke", "regression"})
    public void environmentUrlShouldBeConfigured() {

        String environment = System.getProperty("env", "local");

        if (environment.equalsIgnoreCase("qa")) {

            Assert.assertEquals(
                    Config.BASE_URL,
                    "https://veysauction.onrender.com"
            );

            Assert.assertEquals(
                    Config.API_URL,
                    "https://veysauction.onrender.com"
            );

        } else {

            Assert.assertEquals(
                    Config.BASE_URL,
                    "http://127.0.0.1:8000"
            );

            Assert.assertEquals(
                    Config.API_URL,
                    "http://127.0.0.1:8000"
            );
        }
    }
}
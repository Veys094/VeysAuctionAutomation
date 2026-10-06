package ui;

import com.veysauction.base.BaseTest;
import com.veysauction.pages.LoginPage;
import com.veysauction.utils.JsonTestDataUtil;
import com.veysauction.utils.RetryAnalyzer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    private void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return JsonTestDataUtil.readLoginData(
                "testdata/login-data.json"
        );
    }

    @Test(
            dataProvider = "loginData",
            groups = {"smoke", "regression"}
    )
    public void loginWithJsonData(
            String username,
            String password,
            boolean expectedLogin
    ) {

        System.out.println(
                "TEST: JSON LOGIN DATA"
                        + " | username=" + username
                        + " | expectedLogin=" + expectedLogin
        );

        LoginPage loginPage = new LoginPage(driver);

        loginPage.open();
        pause(1000);

        loginPage.enterUsername(username);
        pause(500);

        loginPage.enterPassword(password);
        pause(500);

        loginPage.clickLogin();
        pause(2000);

        try {
            driver.switchTo().alert().accept();
        } catch (org.openqa.selenium.NoAlertPresentException ignored) {
        }

        pause(500);

        boolean loggedIn =
                !driver.getCurrentUrl().contains("/login");

        assertTrue(
                loggedIn == expectedLogin,
                "Actual login result does not match expected result."
        );

        System.out.println(
                "PASS: expectedLogin=" + expectedLogin
        );
    }

    @Test(
            groups = {"smoke", "regression"},
            retryAnalyzer = RetryAnalyzer.class
    )
    public void validLoginShouldSucceed() {

        System.out.println("TEST: VALID LOGIN");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        pause(1500);

        loginPage.enterUsername("testuser");
        pause(1500);

        loginPage.enterPassword("12345");
        pause(1500);

        loginPage.clickLogin();
        pause(3000);

        assertTrue(
                !driver.getCurrentUrl().contains("/login"),
                "Valid login should redirect away from the login page."
        );

        System.out.println("PASS: Valid login.");
    }

    @Test(groups = {"regression"})
    public void invalidUsernameShouldNotLogin() {

        System.out.println("TEST: INVALID USERNAME");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        pause(1500);

        loginPage.enterUsername("wrong_user_qa_2026");
        pause(1500);

        loginPage.enterPassword("12345");
        pause(1500);

        loginPage.clickLogin();
        pause(2000);

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Invalid username should not log in."
        );

        System.out.println("PASS: Invalid username rejected.");
    }

    @Test(groups = {"regression"})
    public void invalidPasswordShouldNotLogin() {

        System.out.println("TEST: INVALID PASSWORD");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        pause(1500);

        loginPage.enterUsername("testuser");
        pause(1500);

        loginPage.enterPassword("wrongpassword");
        pause(1500);

        loginPage.clickLogin();
        pause(1500);

        try {
            driver.switchTo().alert().accept();
        } catch (org.openqa.selenium.NoAlertPresentException ignored) {
        }

        pause(1000);

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Invalid password should not log in."
        );

        System.out.println("PASS: Invalid password rejected.");
    }

    @Test(groups = {"regression"})
    public void emptyFieldsShouldNotLogin() {

        System.out.println("TEST: EMPTY LOGIN FIELDS");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        pause(1500);

        loginPage.clickLogin();
        pause(2000);

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Login with empty fields should be blocked."
        );

        assertTrue(
                loginPage.isUsernameFieldVisible(),
                "Username field should remain visible."
        );

        assertTrue(
                loginPage.isPasswordFieldVisible(),
                "Password field should remain visible."
        );

        System.out.println("PASS: Empty fields rejected.");
    }
}
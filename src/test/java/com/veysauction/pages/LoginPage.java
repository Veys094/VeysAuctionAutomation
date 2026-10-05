
package com.veysauction.pages;

import com.veysauction.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");

    private final By loginButton =
            By.cssSelector("button[type='submit']");

    private final By errorMessage =
            By.cssSelector(".error-message[role='alert']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(Config.EXPLICIT_WAIT_SECONDS)
        );
    }

    public LoginPage open() {
        driver.get(Config.BASE_URL + "/login");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(usernameInput)
        );

        return this;
    }

    public boolean isUsernameFieldVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(usernameInput)
        ).isDisplayed();
    }

    public boolean isPasswordFieldVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordInput)
        ).isDisplayed();
    }

    public boolean isLoginButtonVisible() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginButton)
        ).isDisplayed();
    }

    public void enterUsername(String username) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(usernameInput)
        );
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(passwordInput)
        );
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    public void submitEmptyForm() {
        clickLogin();
    }

    public boolean isErrorMessageVisible() {
        return !driver.findElements(errorMessage).isEmpty()
                && driver.findElement(errorMessage).isDisplayed();
    }

    public String getErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isLoginPageOpen() {
        return driver.getCurrentUrl().contains("/login");
    }
}
package com.anhtester.pages;

import com.anhtester.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for Login Page
 */
public class LoginPage extends BasePage {
    
    // Locators
    @FindBy(id = "email")
    private WebElement emailInput;
    
    @FindBy(id = "password")
    private WebElement passwordInput;
    
    @FindBy(xpath = "//button[contains(text(),'Login') or @type='submit']")
    private WebElement loginButton;
    
    @FindBy(xpath = "//div[contains(@class,'alert')]")
    private WebElement errorMessage;
    
    @FindBy(xpath = "//a[contains(text(),'Forgot Password')]")
    private WebElement forgotPasswordLink;
    
    /**
     * Constructor
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Login with username and password
     */
    public DashboardPage login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(loginButton);
        return new DashboardPage(driver);
    }
    
    /**
     * Enter email
     */
    public void enterEmail(String email) {
        type(emailInput, email);
    }
    
    /**
     * Enter password
     */
    public void enterPassword(String password) {
        type(passwordInput, password);
    }
    
    /**
     * Click login button
     */
    public void clickLoginButton() {
        click(loginButton);
    }
    
    /**
     * Get error message
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }
    
    /**
     * Click forgot password link
     */
    public void clickForgotPassword() {
        click(forgotPasswordLink);
    }
    
    /**
     * Verify login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        return isDisplayed(emailInput) && isDisplayed(passwordInput);
    }
}

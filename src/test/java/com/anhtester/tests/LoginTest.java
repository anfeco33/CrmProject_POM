package com.anhtester.tests;

import com.anhtester.base.BaseTest;
import com.anhtester.pages.DashboardPage;
import com.anhtester.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class for Login functionality
 */
public class LoginTest extends BaseTest {
    
    @Test(priority = 1, description = "Verify successful login with valid credentials")
    public void testLoginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Verify login page is displayed
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        
        // Login with valid credentials
        String email = getProperty("username");
        String password = getProperty("password");
        DashboardPage dashboardPage = loginPage.login(email, password);
        
        // Verify dashboard is displayed after login
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard should be displayed after successful login");
    }
    
    @Test(priority = 2, description = "Verify login fails with invalid email")
    public void testLoginWithInvalidEmail() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Try to login with invalid email
        loginPage.enterEmail("invalid@example.com");
        loginPage.enterPassword(getProperty("password"));
        loginPage.clickLoginButton();
        
        // Verify error message is displayed or still on login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should remain on login page with invalid credentials");
    }
    
    @Test(priority = 3, description = "Verify login fails with invalid password")
    public void testLoginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Try to login with invalid password
        loginPage.enterEmail(getProperty("username"));
        loginPage.enterPassword("wrongpassword");
        loginPage.clickLoginButton();
        
        // Verify still on login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should remain on login page with invalid credentials");
    }
    
    @Test(priority = 4, description = "Verify login fails with empty credentials")
    public void testLoginWithEmptyCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        
        // Try to login without credentials
        loginPage.clickLoginButton();
        
        // Verify still on login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should remain on login page with empty credentials");
    }
}

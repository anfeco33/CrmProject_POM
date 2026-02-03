package com.anhtester.base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base Page class that all page objects will extend
 * Contains common methods used across all pages
 */
public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    /**
     * Constructor to initialize WebDriver and PageFactory
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Click on an element
     */
    protected void click(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }
    
    /**
     * Type text into an element
     */
    protected void type(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get text from an element
     */
    protected String getText(WebElement element) {
        waitForElementToBeVisible(element);
        return element.getText();
    }
    
    /**
     * Check if element is displayed
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Wait for element to be visible
     */
    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Wait for element to be clickable
     */
    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Wait for element to be invisible
     */
    protected void waitForElementToBeInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    /**
     * Scroll to element
     */
    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * Get current page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current page URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}

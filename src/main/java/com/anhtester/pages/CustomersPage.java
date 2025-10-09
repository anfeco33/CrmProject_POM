package com.anhtester.pages;

import com.anhtester.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for Customers Page
 */
public class CustomersPage extends BasePage {
    
    // Locators
    @FindBy(xpath = "//a[contains(text(),'New Customer') or contains(@class,'btn-primary')]")
    private WebElement newCustomerButton;
    
    @FindBy(id = "company")
    private WebElement companyInput;
    
    @FindBy(id = "vat")
    private WebElement vatInput;
    
    @FindBy(id = "phonenumber")
    private WebElement phoneInput;
    
    @FindBy(id = "website")
    private WebElement websiteInput;
    
    @FindBy(xpath = "//button[contains(text(),'Save') or @type='submit']")
    private WebElement saveButton;
    
    @FindBy(xpath = "//input[@type='search' or contains(@class,'search')]")
    private WebElement searchInput;
    
    @FindBy(xpath = "//table//tbody//tr[1]")
    private WebElement firstCustomerRow;
    
    @FindBy(xpath = "//div[contains(@class,'alert-success')]")
    private WebElement successMessage;
    
    /**
     * Constructor
     */
    public CustomersPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Click New Customer button
     */
    public void clickNewCustomer() {
        click(newCustomerButton);
    }
    
    /**
     * Create a new customer
     */
    public void createCustomer(String company, String vat, String phone, String website) {
        clickNewCustomer();
        type(companyInput, company);
        type(vatInput, vat);
        type(phoneInput, phone);
        type(websiteInput, website);
        click(saveButton);
    }
    
    /**
     * Search for a customer
     */
    public void searchCustomer(String customerName) {
        type(searchInput, customerName);
    }
    
    /**
     * Verify customer is created
     */
    public boolean isCustomerCreated() {
        return isDisplayed(successMessage);
    }
    
    /**
     * Verify customers page is displayed
     */
    public boolean isCustomersPageDisplayed() {
        return isDisplayed(newCustomerButton);
    }
    
    /**
     * Get success message
     */
    public String getSuccessMessage() {
        return getText(successMessage);
    }
}

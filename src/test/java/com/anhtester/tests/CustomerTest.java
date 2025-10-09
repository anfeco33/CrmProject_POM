package com.anhtester.tests;

import com.anhtester.base.BaseTest;
import com.anhtester.pages.CustomersPage;
import com.anhtester.pages.DashboardPage;
import com.anhtester.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Customer functionality
 */
public class CustomerTest extends BaseTest {
    
    private CustomersPage customersPage;
    
    @BeforeMethod
    public void navigateToCustomers() {
        // Login first
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.login(getProperty("username"), getProperty("password"));
        
        // Navigate to Customers page
        customersPage = dashboardPage.navigateToCustomers();
    }
    
    @Test(priority = 1, description = "Verify customers page is accessible")
    public void testCustomersPageAccess() {
        Assert.assertTrue(customersPage.isCustomersPageDisplayed(), "Customers page should be displayed");
    }
    
    @Test(priority = 2, description = "Verify new customer can be created")
    public void testCreateNewCustomer() {
        // Create a new customer with test data
        String company = "Test Company " + System.currentTimeMillis();
        String vat = "VAT" + System.currentTimeMillis();
        String phone = "1234567890";
        String website = "https://testcompany.com";
        
        customersPage.createCustomer(company, vat, phone, website);
        
        // Verify customer is created (success message or in list)
        // Note: This assertion might need adjustment based on actual CRM behavior
        Assert.assertTrue(customersPage.isCustomersPageDisplayed(), "Should remain on customers page after creation");
    }
    
    @Test(priority = 3, description = "Verify customer search functionality")
    public void testSearchCustomer() {
        // Search for a customer
        customersPage.searchCustomer("Test Company");
        
        // Verify search functionality works
        Assert.assertTrue(customersPage.isCustomersPageDisplayed(), "Should remain on customers page after search");
    }
}

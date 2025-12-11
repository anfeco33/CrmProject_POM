package com.an.testcases;

import com.an.common.TestBase;
import com.an.helpers.PropertiesHelper;
import com.an.pages.CustomerPage;
import com.an.pages.DashboardPage;
import com.an.pages.ProjectPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 End-to-end flow covering: Login -> Create Customer -> Search Customer -> Upload File
 */
public class E2EFlowTests extends TestBase {

    @Test
    public void testLogin_CreateCustomer_Search_Upload() throws Exception {
        // Step 1: Login
        DashboardPage dashboard = loginPage.login(
                PropertiesHelper.getValue("email"),
                PropertiesHelper.getValue("password")
        );
        Assert.assertTrue(dashboard.isAt(), "Should land on Dashboard after successful login");

        // Step 2: Go to Customers page
        CustomerPage customerPage = dashboard.menuCustomerClick();
        Assert.assertTrue(customerPage.isAt(), "Should be on Customers page");

        // Step 3: Open Add New Customer form
        Assert.assertTrue(customerPage.addNewCustomerClick(), "Should navigate to Add New Customer form");

        // Step 4: Create a unique customer
        String uniqueCustomerName = "E2E Auto Co " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Assert.assertTrue(customerPage.createCustomer(uniqueCustomerName), "Customer should be created successfully");

        // Step 5: Search for the newly created customer
        Assert.assertTrue(customerPage.searchCustomer(uniqueCustomerName), "Newly created customer should appear in search results");

        // Step 6: Upload a file to the customer's attachments
        Assert.assertTrue(customerPage.uploadFile(), "File should be uploaded successfully for the customer");

        // Step 7: Go to Projects page
        ProjectPage projectPage = dashboard.menuProjectClick();
        Assert.assertTrue(projectPage.isAt(), "Should be on Projects page");

        // Step 8: Open Create New Project form

    }
}

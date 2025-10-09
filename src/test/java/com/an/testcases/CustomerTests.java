package com.an.testcases;

import com.an.common.TestBase;
import com.an.pages.CustomerPage;
import com.an.helpers.PropertiesHelper;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomerTests extends TestBase {
    private CustomerPage customerPage;
    private String customerName = "CM Co., Ltd";

//    @Test(priority = 1)
//    public void testCustomerFormNavigation() throws Exception {
//        customerPage = loginPage.login("admin@example.com", "123456").menuCustomerClick();
//        Assert.assertTrue(customerPage.addNewCustomerClick(), "Add New Customer button should navigate to the Add Customer page");
//    }

    @Test
    public void testCreateCustomer() throws Exception {
        customerPage = loginPage.login(PropertiesHelper.getValue("email"), PropertiesHelper.getValue("password")).menuCustomerClick();
        customerPage.addNewCustomerClick();

        Assert.assertTrue(customerPage.createCustomer(customerName), "Customer should be created successfully");
        Assert.assertTrue(customerPage.searchCustomer(customerName), "Customer should be found in the customer list");
    }

    @Test (dependsOnMethods = {"testCreateCustomer"})
    public void testFileUpload() throws Exception {
        customerPage = loginPage.login(PropertiesHelper.getValue("email"), PropertiesHelper.getValue("password")).menuCustomerClick();
        // Filter the table to the specific customer created in the previous test for stability
        Assert.assertTrue(customerPage.searchCustomer(customerName), "Customer should be found before uploading file");
        Assert.assertTrue(customerPage.uploadFile(), "File should be uploaded successfully for the customer");
    }

}

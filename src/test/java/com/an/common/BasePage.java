package com.an.common;

import com.an.driver.DriverManager;
import com.an.helpers.PropertiesHelper;
import com.an.helpers.WebElementHelper;
import com.an.pages.CustomerPage;
import com.an.pages.DashboardPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebElementHelper webElementHelper;

    @FindBy(xpath = "//span[normalize-space()='Dashboard']")
    public WebElement menuDashboard;
    @FindBy(xpath = "//span[normalize-space()='Customers']")
    public WebElement menuCustomers;
    @FindBy(xpath = "//span[normalize-space()='Projects']")
    public WebElement menuProjects;
    @FindBy(xpath = "//a[contains(@class,'notifications-icon')]")
    public WebElement itemNotifications;

    protected BasePage() {
        PropertiesHelper.loadAllFiles();
        this.webElementHelper = new WebElementHelper();
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public DashboardPage menuDashboardClick() {
        webElementHelper.clickElement(menuDashboard);

        return new DashboardPage();
    }

    public CustomerPage menuCustomerClick() {
        webElementHelper.clickElement(menuCustomers);

        return new CustomerPage();
    }
}

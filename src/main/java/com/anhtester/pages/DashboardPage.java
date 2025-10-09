package com.anhtester.pages;

import com.anhtester.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for Dashboard Page
 */
public class DashboardPage extends BasePage {
    
    // Locators
    @FindBy(xpath = "//span[contains(text(),'Dashboard')]")
    private WebElement dashboardMenu;
    
    @FindBy(xpath = "//a[contains(@href,'customers')]")
    private WebElement customersMenu;
    
    @FindBy(xpath = "//a[contains(@href,'projects')]")
    private WebElement projectsMenu;
    
    @FindBy(xpath = "//a[contains(@href,'tasks')]")
    private WebElement tasksMenu;
    
    @FindBy(xpath = "//li[@class='icon header-user-profile']")
    private WebElement userProfileIcon;
    
    @FindBy(xpath = "//a[contains(text(),'Logout') or contains(@href,'logout')]")
    private WebElement logoutLink;
    
    @FindBy(xpath = "//h4[contains(text(),'Dashboard') or contains(@class,'page-title')]")
    private WebElement pageTitle;
    
    /**
     * Constructor
     */
    public DashboardPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigate to Customers page
     */
    public CustomersPage navigateToCustomers() {
        click(customersMenu);
        return new CustomersPage(driver);
    }
    
    /**
     * Navigate to Projects page
     */
    public ProjectsPage navigateToProjects() {
        click(projectsMenu);
        return new ProjectsPage(driver);
    }
    
    /**
     * Navigate to Tasks page
     */
    public TasksPage navigateToTasks() {
        click(tasksMenu);
        return new TasksPage(driver);
    }
    
    /**
     * Logout from application
     */
    public void logout() {
        click(userProfileIcon);
        click(logoutLink);
    }
    
    /**
     * Verify dashboard page is displayed
     */
    public boolean isDashboardDisplayed() {
        return isDisplayed(dashboardMenu);
    }
    
    /**
     * Get page title
     */
    public String getDashboardTitle() {
        return getPageTitle();
    }
}

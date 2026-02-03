package com.anhtester.pages;

import com.anhtester.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for Projects Page
 */
public class ProjectsPage extends BasePage {
    
    // Locators
    @FindBy(xpath = "//a[contains(text(),'New Project') or contains(@class,'btn-primary')]")
    private WebElement newProjectButton;
    
    @FindBy(id = "name")
    private WebElement projectNameInput;
    
    @FindBy(id = "clientid")
    private WebElement customerDropdown;
    
    @FindBy(id = "start_date")
    private WebElement startDateInput;
    
    @FindBy(id = "deadline")
    private WebElement deadlineInput;
    
    @FindBy(xpath = "//button[contains(text(),'Save') or @type='submit']")
    private WebElement saveButton;
    
    @FindBy(xpath = "//input[@type='search' or contains(@class,'search')]")
    private WebElement searchInput;
    
    @FindBy(xpath = "//div[contains(@class,'alert-success')]")
    private WebElement successMessage;
    
    @FindBy(xpath = "//table//tbody//tr[1]")
    private WebElement firstProjectRow;
    
    /**
     * Constructor
     */
    public ProjectsPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Click New Project button
     */
    public void clickNewProject() {
        click(newProjectButton);
    }
    
    /**
     * Create a new project
     */
    public void createProject(String projectName, String startDate, String deadline) {
        clickNewProject();
        type(projectNameInput, projectName);
        type(startDateInput, startDate);
        type(deadlineInput, deadline);
        click(saveButton);
    }
    
    /**
     * Search for a project
     */
    public void searchProject(String projectName) {
        type(searchInput, projectName);
    }
    
    /**
     * Verify project is created
     */
    public boolean isProjectCreated() {
        return isDisplayed(successMessage);
    }
    
    /**
     * Verify projects page is displayed
     */
    public boolean isProjectsPageDisplayed() {
        return isDisplayed(newProjectButton);
    }
    
    /**
     * Get success message
     */
    public String getSuccessMessage() {
        return getText(successMessage);
    }
}

package com.anhtester.pages;

import com.anhtester.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for Tasks Page
 */
public class TasksPage extends BasePage {
    
    // Locators
    @FindBy(xpath = "//a[contains(text(),'New Task') or contains(@class,'btn-primary')]")
    private WebElement newTaskButton;
    
    @FindBy(id = "name")
    private WebElement taskNameInput;
    
    @FindBy(id = "startdate")
    private WebElement startDateInput;
    
    @FindBy(id = "duedate")
    private WebElement dueDateInput;
    
    @FindBy(id = "priority")
    private WebElement priorityDropdown;
    
    @FindBy(xpath = "//button[contains(text(),'Save') or @type='submit']")
    private WebElement saveButton;
    
    @FindBy(xpath = "//input[@type='search' or contains(@class,'search')]")
    private WebElement searchInput;
    
    @FindBy(xpath = "//div[contains(@class,'alert-success')]")
    private WebElement successMessage;
    
    @FindBy(xpath = "//table//tbody//tr[1]")
    private WebElement firstTaskRow;
    
    /**
     * Constructor
     */
    public TasksPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Click New Task button
     */
    public void clickNewTask() {
        click(newTaskButton);
    }
    
    /**
     * Create a new task
     */
    public void createTask(String taskName, String startDate, String dueDate) {
        clickNewTask();
        type(taskNameInput, taskName);
        type(startDateInput, startDate);
        type(dueDateInput, dueDate);
        click(saveButton);
    }
    
    /**
     * Search for a task
     */
    public void searchTask(String taskName) {
        type(searchInput, taskName);
    }
    
    /**
     * Verify task is created
     */
    public boolean isTaskCreated() {
        return isDisplayed(successMessage);
    }
    
    /**
     * Verify tasks page is displayed
     */
    public boolean isTasksPageDisplayed() {
        return isDisplayed(newTaskButton);
    }
    
    /**
     * Get success message
     */
    public String getSuccessMessage() {
        return getText(successMessage);
    }
}

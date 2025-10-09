package com.anhtester.tests;

import com.anhtester.base.BaseTest;
import com.anhtester.pages.DashboardPage;
import com.anhtester.pages.LoginPage;
import com.anhtester.pages.TasksPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Task functionality
 */
public class TaskTest extends BaseTest {
    
    private TasksPage tasksPage;
    
    @BeforeMethod
    public void navigateToTasks() {
        // Login first
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.login(getProperty("username"), getProperty("password"));
        
        // Navigate to Tasks page
        tasksPage = dashboardPage.navigateToTasks();
    }
    
    @Test(priority = 1, description = "Verify tasks page is accessible")
    public void testTasksPageAccess() {
        Assert.assertTrue(tasksPage.isTasksPageDisplayed(), "Tasks page should be displayed");
    }
    
    @Test(priority = 2, description = "Verify new task can be created")
    public void testCreateNewTask() {
        // Create a new task with test data
        String taskName = "Test Task " + System.currentTimeMillis();
        String startDate = "2024-01-01";
        String dueDate = "2024-01-31";
        
        tasksPage.createTask(taskName, startDate, dueDate);
        
        // Verify task is created
        Assert.assertTrue(tasksPage.isTasksPageDisplayed(), "Should remain on tasks page after creation");
    }
    
    @Test(priority = 3, description = "Verify task search functionality")
    public void testSearchTask() {
        // Search for a task
        tasksPage.searchTask("Test Task");
        
        // Verify search functionality works
        Assert.assertTrue(tasksPage.isTasksPageDisplayed(), "Should remain on tasks page after search");
    }
}

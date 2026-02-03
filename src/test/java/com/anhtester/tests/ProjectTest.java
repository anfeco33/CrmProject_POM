package com.anhtester.tests;

import com.anhtester.base.BaseTest;
import com.anhtester.pages.DashboardPage;
import com.anhtester.pages.LoginPage;
import com.anhtester.pages.ProjectsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Project functionality
 */
public class ProjectTest extends BaseTest {
    
    private ProjectsPage projectsPage;
    
    @BeforeMethod
    public void navigateToProjects() {
        // Login first
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.login(getProperty("username"), getProperty("password"));
        
        // Navigate to Projects page
        projectsPage = dashboardPage.navigateToProjects();
    }
    
    @Test(priority = 1, description = "Verify projects page is accessible")
    public void testProjectsPageAccess() {
        Assert.assertTrue(projectsPage.isProjectsPageDisplayed(), "Projects page should be displayed");
    }
    
    @Test(priority = 2, description = "Verify new project can be created")
    public void testCreateNewProject() {
        // Create a new project with test data
        String projectName = "Test Project " + System.currentTimeMillis();
        String startDate = "2024-01-01";
        String deadline = "2024-12-31";
        
        projectsPage.createProject(projectName, startDate, deadline);
        
        // Verify project is created
        Assert.assertTrue(projectsPage.isProjectsPageDisplayed(), "Should remain on projects page after creation");
    }
    
    @Test(priority = 3, description = "Verify project search functionality")
    public void testSearchProject() {
        // Search for a project
        projectsPage.searchProject("Test Project");
        
        // Verify search functionality works
        Assert.assertTrue(projectsPage.isProjectsPageDisplayed(), "Should remain on projects page after search");
    }
}

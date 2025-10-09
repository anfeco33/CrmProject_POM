package com.an.testcases;

import com.an.common.TestBase;
import com.an.helpers.PropertiesHelper;
import com.an.pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardTests extends TestBase {
    private DashboardPage dashboardPage;

    @Test (priority = 1)
    public void testViewAllLink() throws Exception {
        dashboardPage = loginPage.login("admin@example.com", "123456");
        Assert.assertTrue(dashboardPage.viewAllClick(), "View All link should navigate to the To Do page");
    }

    @Test
    public void testNewToDoButton() throws Exception {
        dashboardPage = loginPage.login(PropertiesHelper.getValue("email"), PropertiesHelper.getValue("password"));
        Assert.assertTrue(dashboardPage.viewAllClick(), "View All link should navigate to the To Do page before opening modal");
        Assert.assertTrue(dashboardPage.newToDoClick(), "New To Do button should open the New To Do modal");
    }

    @Test
    public void testToDoCreation() throws Exception {
        dashboardPage = loginPage.login("admin@example.com", "123456");
        Assert.assertTrue(dashboardPage.createToDo("-do .."), "To Do item should be created successfully");
    }
}

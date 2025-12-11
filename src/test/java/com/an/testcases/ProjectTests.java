package com.an.testcases;

import com.an.common.TestBase;
import com.an.helpers.CaptureHelper;
import com.an.helpers.PropertiesHelper;
import com.an.pages.ProjectPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ProjectTests extends TestBase {
    private ProjectPage projectPage;

    private String projectName = "#Test 909a " + System.currentTimeMillis();
    private String customerName = "E2E Auto Co";
    private String ratePerHour = "100";
    private String estimatedHours = "10";
    private String des = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus at lobortis lorem, " +
            "non auctor neque. Quisque semper ornare ipsum. Donec blandit purus magna, " +
            "condimentum fringilla metus rhoncus pellentesque. Nulla posuere mollis elit ut rutrum. " +
            "Pellentesque varius quam eget arcu aliquet viverra eget in metus. Nulla bibendum nibh ut orci tincidunt rutrum. " +
            "Quisque condimentum velit quis lectus lacinia blandit. Suspendisse sodales sodales sagittis. " +
            "Duis sollicitudin luctus porta. Ut justo lacus, scelerisque nec interdum ut, hendrerit quis risus. " +
            "Suspendisse vel lectus et libero pretium vestibulum at at ipsum. Suspendisse scelerisque placerat lorem, " +
            "nec gravida magna efficitur sed. In scelerisque vulputate tortor quis interdum.";

    @Test
    public void testCreateProject(Method method) throws Exception {
        CaptureHelper.startRecord(method.getName());
        projectPage = loginPage.login(PropertiesHelper.getValue("email"), PropertiesHelper.getValue("password")).menuProjectClick();

        Assert.assertTrue(projectPage.createNewProject(projectName, customerName, ratePerHour, estimatedHours, des), "Project should be created successfully");
    }

}

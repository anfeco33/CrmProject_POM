package com.an.common;

import com.an.driver.DriverManager;
import com.an.helpers.CaptureHelper;
import com.an.helpers.PropertiesHelper;
import com.an.listeners.TestListener;
import com.an.pages.LoginPage;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

/**
 * Shared per-test setup/teardown to reduce duplication across test classes.
 * Ensures each test starts on the Login page with a clean session.
 */
@Listeners(TestListener.class)
public abstract class TestBase extends BaseSetup {
    protected LoginPage loginPage;

    @BeforeMethod
    public void commonBeforeEach() {
//        PropertiesHelper.loadAllFiles();

        try { DriverManager.getDriver().manage().deleteAllCookies(); } catch (Exception ignored) {}
        DriverManager.getDriver().navigate().to(getAppURL());
        loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isAt(), "Should be on Login page before each test");
    }

    @AfterMethod
    public void takeScreenshot(ITestResult result) {
        // Screenshot capture is now handled centrally in TestListener.onTestFailure
        // to avoid creating duplicate images and race conditions.
    }

    @AfterMethod(alwaysRun = true)
    public void commonAfterEach() {
        CaptureHelper.stopRecord();
        try { DriverManager.getDriver().manage().deleteAllCookies(); } catch (Exception ignored) {}
    }
}

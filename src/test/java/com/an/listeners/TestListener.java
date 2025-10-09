package com.an.listeners;

import com.an.helpers.CaptureHelper;
import com.an.helpers.PropertiesHelper;
import com.an.reports.ExtentReportManager;
import com.an.reports.ExtentTestManager;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.*;

public class TestListener implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        PropertiesHelper.loadAllFiles();
        // Initialize ExtentReports at suite start
        ExtentReportManager.getExtentReports();
    }

    @Override
    public void onFinish(ISuite suite) {
        // Flush the report when the suite finishes
        try {
            ExtentReportManager.getExtentReports().flush();
        } catch (Exception ignored) {}
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        if (description == null || description.isEmpty()) {
            description = result.getMethod().getQualifiedName();
        }
        ExtentTestManager.saveToReport(testName, description);
        ExtentTestManager.logMessage(Status.INFO, "Test started: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.logMessage(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.logMessage(Status.FAIL, "Test failed: " + getThrowableMessage(result));
        try {
            // Give the UI a short chance to finish rendering/AJAX before capturing
            waitForUiToSettle();
            // Capture screenshot using CaptureHelper and attach that exact file to report
            String methodName = result.getMethod().getMethodName();
            String filePath = CaptureHelper.captureScreenshot(methodName);
            ExtentTestManager.addScreenshotFromFilePath(Status.FAIL, "Screenshot on failure", filePath);
        } catch (Exception ignored) {}
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.logMessage(Status.SKIP, "Test skipped: " + getThrowableMessage(result));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ExtentTestManager.logMessage(Status.WARNING, "Test failed but within success percentage");
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    private String getThrowableMessage(ITestResult result) {
        Throwable t = result.getThrowable();
        return t == null ? "" : t.getMessage();
    }

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    // Wait briefly for page/JS/AJAX to settle so the screenshot reflects the final state
    private void waitForUiToSettle() {
        try {
            WebDriver driver = com.an.driver.DriverManager.getDriver();
            if (driver == null) return;
            long end = System.currentTimeMillis() + 2500; // up to 2.5s
            while (System.currentTimeMillis() < end) {
                boolean docReady = false;
                boolean ajaxIdle = true;
                try {
                    Object readyState = ((JavascriptExecutor) driver).executeScript("return document.readyState");
                    docReady = "complete".equals(readyState);
                } catch (Exception ignored) {}
                try {
                    Object ajax = ((JavascriptExecutor) driver).executeScript("return (window.jQuery && window.jQuery.active) || 0;");
                    long active = (ajax instanceof Number) ? ((Number) ajax).longValue() : 0L;
                    ajaxIdle = active == 0L;
                } catch (Exception ignored) {}

                if (docReady && ajaxIdle) {
                    try { Thread.sleep(200); } catch (InterruptedException ignored) {}
                    break;
                }
                try { Thread.sleep(150); } catch (InterruptedException ignored) {}
            }
        } catch (Exception ignored) {}
    }
}
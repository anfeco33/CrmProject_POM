package com.an.reports;

import com.an.driver.DriverManager;
import com.an.helpers.PropertiesHelper;
import com.an.helpers.SystemHelper;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    static ExtentReports extent = ExtentReportManager.getExtentReports();

    public static ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized ExtentTest saveToReport(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }

    public static void addScreenshot(String message) {
        String base64Image = "data:image/png;base64,"
                + ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);

        getTest().log(Status.INFO, message,
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
    }

    public static void addScreenshot(Status status, String message) {
        String base64Image = "data:image/png;base64,"
                + ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);

        getTest().log(status, message,
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
    }

    // Attach the latest screenshot created by CaptureHelper from test-output/screenshots as BASE64
    public static void addScreenshotFromCaptureHelper(Status status, String message) {
        String base64FromFile = getLatestScreenshotAsBase64();
        if (base64FromFile != null) {
            getTest().log(status, message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String("data:image/png;base64," + base64FromFile).build());
            return;
        }
        // Fallback to live WebDriver screenshot if no file is found
        addScreenshot(status, message);
    }

    // Attach a specific local file path (absolute) as Base64 image
    public static void addScreenshotFromFilePath(Status status, String message, String absoluteFilePath) {
        if (absoluteFilePath == null || absoluteFilePath.trim().isEmpty()) {
            addScreenshotFromCaptureHelper(status, message);
            return;
        }
        try {
            File file = new File(absoluteFilePath);
            if (!file.exists() || !file.isFile()) {
                addScreenshotFromCaptureHelper(status, message);
                return;
            }
            byte[] bytes = Files.readAllBytes(file.toPath());
            String base64 = Base64.getEncoder().encodeToString(bytes);
            getTest().log(status, message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String("data:image/png;base64," + base64).build());
        } catch (IOException e) {
            addScreenshotFromCaptureHelper(status, message);
        }
    }

    private static String getLatestScreenshotAsBase64() {
        try {
            String relPath = PropertiesHelper.getValue("SCREENSHOT_PATH");
            if (relPath == null || relPath.trim().isEmpty()) return null;
            File dir = new File(SystemHelper.getCurrentDir() + relPath);
            if (!dir.exists() || !dir.isDirectory()) return null;

            File[] pngs = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".png"));
            if (pngs == null || pngs.length == 0) return null;

            File latest = Arrays.stream(pngs)
                    .max(Comparator.comparingLong(File::lastModified))
                    .orElse(null);
            if (latest == null) return null;
            byte[] bytes = Files.readAllBytes(latest.toPath());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException ignored) {
            return null;
        }
    }

    public static void logMessage(String message) {
        getTest().log(Status.INFO, message);
    }

    public static void logMessage(Status status, String message) {
        getTest().log(status, message);
    }
}

package com.anhtester.utils;

import com.anhtester.base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for capturing screenshots
 */
public class ScreenshotUtil {
    
    /**
     * Capture screenshot and save to file
     * @param testName Name of the test
     * @return Path to the screenshot file
     */
    public static String captureScreenshot(String testName) {
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File("test-output/screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String screenshotName = testName + "_" + timestamp + ".png";
            String screenshotPath = "test-output/screenshots/" + screenshotName;
            
            TakesScreenshot screenshot = (TakesScreenshot) BaseTest.driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            File destination = new File(screenshotPath);
            
            FileUtils.copyFile(source, destination);
            
            return screenshotPath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

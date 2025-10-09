package com.an.reports;

import com.an.helpers.SystemHelper;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;

public class ExtentReportManager {

    private static final ExtentReports extentReports = new ExtentReports();
    private static boolean initialized = false;

    public synchronized static ExtentReports getExtentReports() {
        if (!initialized) {
            String outputDirPath = SystemHelper.getCurrentDir() + File.separator + "test-output";
            File outputDir = new File(outputDirPath);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            String reportPath = outputDirPath + File.separator + "ExtentReport.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            // Ensure charts and UI assets load without Internet
            reporter.config().setOfflineMode(true);
            reporter.config().setDocumentTitle("Extent Report | CRM System");
            reporter.config().setReportName("Extent Report | CRM System");
            reporter.config().setTheme(Theme.STANDARD);

            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("Framework Name", "Selenium WebDriver + TestNG");
            extentReports.setSystemInfo("Author", "An");
            initialized = true;
        }
        return extentReports;
    }

}


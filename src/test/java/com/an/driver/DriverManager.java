package com.an.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driver) {
        DriverManager.driver.set(driver);
    }

    public static void resetImplicitlyWait() {
        DriverManager.driver.get().manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(0));
    }

    public static void quit() {
        DriverManager.driver.get().quit();
        driver.remove();
    }
}

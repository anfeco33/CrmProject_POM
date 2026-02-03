package com.anhtester.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * Base Test class that all test classes will extend
 * Handles WebDriver initialization and teardown
 */
public class BaseTest {
    
    public static WebDriver driver;
    protected static Properties properties;
    
    static {
        loadProperties();
    }
    
    /**
     * Load configuration properties from config.properties file
     */
    private static void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Initialize WebDriver before each test method
     */
    @BeforeMethod
    public void setUp() {
        String browser = properties.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(properties.getProperty("headless", "false"));
        
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
                
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
        
        // Set timeouts
        int implicitWait = Integer.parseInt(properties.getProperty("implicit.wait", "10"));
        int pageLoadTimeout = Integer.parseInt(properties.getProperty("page.load.timeout", "30"));
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        driver.manage().window().maximize();
        
        // Navigate to application URL
        driver.get(properties.getProperty("app.url"));
    }
    
    /**
     * Quit WebDriver after each test method
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    /**
     * Get property value from config.properties
     */
    protected String getProperty(String key) {
        return properties.getProperty(key);
    }
}

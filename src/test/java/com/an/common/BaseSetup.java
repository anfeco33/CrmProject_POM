package com.an.common;

import com.an.driver.DriverManager;
import com.an.helpers.PropertiesHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.*;
import org.testng.thread.IThreadWorkerFactory;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BaseSetup {

    private String appURL;
    boolean isCI = System.getenv("CI") != null;

    protected String getAppURL() {
        return appURL;
    }

    // Hàm này để tùy chọn Browser. Cho chạy trước khi gọi class này (BeforeClass)
    private WebDriver createDriver(String browserType) {
        WebDriver driver;
        switch (browserType) {
            case "chrome":
                driver = initChromeDriver();
                break;
            case "firefox":
                driver = initFirefoxDriver();
                break;
            case "edge":
                driver = initEdgeDriver();
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver();
        }
        return driver;
    }

    private static WebDriver initChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // ẩn "Chrome is being controlled..."
        // options.setExperimentalOption("excludeSwitches",
        // Collections.singletonList("enable-automation"));

        // Thiết lập prefs
        Map<String, Object> prefs = new HashMap<>();
        // Tắt dịch vụ quản lý thông tin đăng nhập và trình quản lý mật khẩu
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        prefs.put("profile.password_manager_leak_detection", false);
        // Tắt tính năng tự động điền (autofill)
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // Thêm các đối số dòng lệnh để vô hiệu hóa triệt để các tính năng liên quan
        options.addArguments(Arrays.asList(
//                "--window-size=1920,1080", // Giả lập màn hình
                "--disable-features=PasswordManagerUI", // Tắt UI trình quản lý mật khẩu
                "--disable-save-password-bubble", // Tắt bong bóng "Lưu mật khẩu"
                "--password-store=basic", // Dùng kho lưu trữ mật khẩu cơ bản
                "--disable-sync" // Tắt đồng bộ hóa, tránh ghi đè cài đặt
        ));

        WebDriver driver = new ChromeDriver(options);
        return driver;
    }

    private static WebDriver initFirefoxDriver() {
        System.out.println("Launching Firefox browser...");
        WebDriverManager.firefoxdriver().setup();

        // Tạo FirefoxProfile để chứa tùy chỉnh
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("signon.rememberSignons", false);
        profile.setPreference("dom.forms.autocomplete.formautofill", false);
        profile.setPreference("extensions.formautofill.addresses.enabled", false);
        profile.setPreference("extensions.formautofill.creditCards.enabled", false);

        // Gán profile vào FirefoxOptions
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);

        if (System.getenv("CI") != null) {
            options.addArguments("-headless");
        }

        WebDriver driver = new FirefoxDriver(options);
        return driver;
    }

    private static String findOnPath(String exeName) {
        String pathEnv = System.getenv("PATH");
        if (pathEnv == null || pathEnv.isEmpty()) return null;
        String[] dirs = pathEnv.split(";");
        for (String dir : dirs) {
            if (dir == null || dir.isEmpty()) continue;
            File candidate = new File(dir.trim(), exeName);
            if (candidate.exists() && candidate.isFile()) {
                return candidate.getAbsolutePath();
            }
        }
        return null;
    }

    private static WebDriver initEdgeDriver() {
        System.out.println("Launching Edge browser (prefer PATH, fallback WDM)...");

        EdgeOptions options = new EdgeOptions();

        // Tùy chỉnh Edge (prefs)
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // Headless khi CI để ổn định
        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        }

        // Ưu tiên EdgeDriver từ PATH
        String onPath = findOnPath("msedgedriver.exe");
        if (onPath != null) {
            System.setProperty("webdriver.edge.driver", onPath);
            System.out.println("[Edge Setup] Using msedgedriver from PATH: " + onPath);
            return new EdgeDriver(options);
        }

        // Fallback: dùng WebDriverManager (cần mạng). fail nếu DNS/CDN sự cố
        System.out.println("[Edge Setup] msedgedriver.exe not found on PATH. Falling back to WebDriverManager...");
        try {
            io.github.bonigarcia.wdm.WebDriverManager.edgedriver().setup();
            return new EdgeDriver(options);
        } catch (Exception e) {
            System.err.println("[Edge Setup] WebDriverManager setup failed: " + e.getMessage());
            System.err.println("Hint: Trên CI, hãy đảm bảo bước cài đặt Edge WebDriver (Chocolatey) đã chạy thành công.");
            throw e;
        }
    }

//    private static WebDriver initEdgeDriver() {
//        System.out.println("Launching Edge browser (PATH mode)...");
//
//        EdgeOptions options = new EdgeOptions();
//
//        // tùy chỉnh Edge (prefs)
//        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("credentials_enable_service", false);
//        prefs.put("profile.password_manager_enabled", false);
//        prefs.put("profile.password_manager_leak_detection", false);
//        prefs.put("autofill.profile_enabled", false);
//        options.setExperimentalOption("prefs", prefs);
//
//        // msedgedriver.exe từ PATH
//        String driverPath = findOnPath("msedgedriver.exe");
//        if (driverPath == null || driverPath.isEmpty()) {
//            throw new RuntimeException(
//                    "msedgedriver.exe not found on PATH. Please place msedgedriver.exe into a folder listed in PATH (e.g., C:\\\\WebDrivers) and restart your IDE/terminal.");
//        }
//        System.setProperty("webdriver.edge.driver", driverPath);
//        System.out.println("[Edge Setup] Using msedgedriver from PATH: " + driverPath);
//
//        return new EdgeDriver(options);
//    }

    // Chạy hàm này đầu tiên khi class này được gọi
    @Parameters({ "browserType", "appURL" })
    @BeforeMethod
    public void initializeTestBaseSetup(@org.testng.annotations.Optional("chrome") String browserType,
                                        @org.testng.annotations.Optional("https://crm.anhtester.com/admin/authentication") String appURL) {
            this.appURL = appURL;

            // Ưu tiên trc từ dòng lệnh (CI)
            String sysBrowser = System.getProperty("browser");
            if (sysBrowser != null && !sysBrowser.isEmpty()) {
                browserType = sysBrowser;
            } else {
                // Ưu tiên 2 đọc từ file cấu hình
                String configBrowser = PropertiesHelper.getValue("browser");
                if (configBrowser != null && !configBrowser.isEmpty()) {
                    browserType = configBrowser;
                }
            }

            WebDriver driver = createDriver(browserType.trim().toLowerCase());
            DriverManager.setDriver(driver); // Set to ThreadLocal

            if (driver == null) {
                throw new IllegalStateException("Failed to initialize WebDriver for browserType=" + browserType + ". Check driver binary and compatibility.");
            }

            driver.navigate().to(appURL);
            driver.manage().window().maximize();

            // Set timeouts
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(Integer.parseInt(PropertiesHelper.getValue("PAGE_LOAD_TIMEOUT"))));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(PropertiesHelper.getValue("PAGE_LOAD_TIMEOUT"))));
            // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @AfterMethod
    public void tearDown() throws Exception {
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (DriverManager.getDriver() != null) {
//            DriverManager.resetImplicitlyWait();
            DriverManager.quit();
        }
    }
}

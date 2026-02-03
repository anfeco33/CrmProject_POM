package com.an.helpers;

import com.an.driver.DriverManager;
import com.an.reports.ExtentTestManager;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class WebElementHelper {

    private static final Logger LOG = LoggerFactory.getLogger(WebElementHelper.class);
    private WebDriverWait wait;

    private final int TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("TIMEOUT"));
    private final Duration pageLoadTimeout = Duration.ofSeconds(Integer.parseInt(PropertiesHelper.getValue("PAGE_LOAD_TIMEOUT")));

    // region Resolve element helpers
    public WebElement getWebElement(By by) {
        return DriverManager.getDriver().findElement(by);
    }
    public List<WebElement> getWebElements(By by) {
        return DriverManager.getDriver().findElements(by);
    }
    // endregion

    // region Actions - WebElement
    public void setText(WebElement e, String value) {
        wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));

        wait.until(ExpectedConditions.visibilityOf(e));
        e.sendKeys(value);
        try {
            ExtentTestManager.logMessage(Status.PASS, "Set text '" + value + "' on element: " + safeElementToString(e));
        } catch (Exception ignored) {}
    }

    public void clickElement(WebElement e) {
        wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));

        waitUntilClickable(e, Duration.ofSeconds(TIMEOUT));
        e.click();
        try {
            ExtentTestManager.logMessage(Status.PASS, "Click element: " + safeElementToString(e));
        } catch (Exception ignored) {}
    }

    public boolean verifyElementDisplayed(WebElement e) {
        try {
            return e.isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean waitUntilVisible(WebElement e, Duration timeout) {
        try {
            new WebDriverWait(DriverManager.getDriver(), timeout).until(ExpectedConditions.visibilityOf(e));
            return true;
        } catch (Exception ex) {
            LOG.warn("[waitUntilVisible] Element did not become visible within {}s", timeout.toSeconds());
            return false;
        }
    }

    public boolean waitUntilClickable(WebElement e, Duration timeout) {
        try {
            new WebDriverWait(DriverManager.getDriver(), timeout).until(ExpectedConditions.elementToBeClickable(e));
            return true;
        } catch (Exception ex) {
            LOG.warn("[waitUntilClickable] Element was not clickable within {}s", timeout.toSeconds());
            return false;
        }
    }

    public void scrollIntoView(WebElement e) {
        try {
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", e);
            try { ExtentTestManager.logMessage(Status.INFO, "Scroll into view: " + safeElementToString(e)); } catch (Exception ignored) {}
        } catch (Exception ex) {
            LOG.debug("[scrollIntoView] Ignored: {}", ex.toString());
        }
    }

    public boolean jsClick(WebElement e) {
        try {
            ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", e);
            try { ExtentTestManager.logMessage(Status.PASS, "JS click on element: " + safeElementToString(e)); } catch (Exception ignored) {}
            return true;
        } catch (Exception ex) {
            LOG.warn("[jsClick] Failed to click via JS: {}", ex.toString());
            return false;
        }
    }

    public boolean verifyElementText(WebElement e, String textValue) {
        wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));

        wait.until(ExpectedConditions.visibilityOf(e));
        return e.getText().equals(textValue);
    }

    public void setKey(WebElement e, CharSequence key) {
        wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));

        wait.until(ExpectedConditions.visibilityOf(e));
        e.sendKeys(key);
    }

    public void setKey(By by, CharSequence key) {
        WebElement e = getWebElement(by);
        setKey(e, key);
    }

    public void delayFor(long seconds) {
        try {
            Thread.sleep((long) (1000 * seconds));
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    // endregion

    // region Actions - By (overloads)
    public void setText(By by, String value) {
        WebElement e = getWebElement(by);
        setText(e, value);
        try {
            ExtentTestManager.logMessage(Status.PASS, "Set text '" + value + "' on element " + by);
        } catch (Exception ignored) {}
    }

    public void clickElement(By by) {
        try {
            wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT));

            WebElement e = wait.until(ExpectedConditions.elementToBeClickable(by));
            e.click();
            ExtentTestManager.logMessage(Status.PASS, "Click element " + by);
        } catch (StaleElementReferenceException ex) {
            WebElement e = DriverManager.getDriver().findElement(by);
            e.click();
        } catch (Exception ignored) {}
    }

    public void rightClickElement(By by) {
        WebElement e = getWebElement(by);
        wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
        wait.until(ExpectedConditions.visibilityOf(e));
        Actions action = new Actions(DriverManager.getDriver());
        action.contextClick(e).perform();
        try {
            ExtentTestManager.logMessage(Status.PASS, "Right click on element " + by);
        } catch (Exception ignored) {}
    }

    public boolean verifyElementDisplayed(By by) {
        try {
            return getWebElement(by).isDisplayed();
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean waitUntilVisible(By by, Duration timeout) {
        try {
            new WebDriverWait(DriverManager.getDriver(), timeout).until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception ex) {
            LOG.warn("[waitUntilVisible] Element located by {} did not become visible within {}s", by, timeout.toSeconds());
            return false;
        }
    }

    public boolean waitUntilClickable(By by, Duration timeout) {
        try {
            new WebDriverWait(DriverManager.getDriver(), timeout).until(ExpectedConditions.elementToBeClickable(by));
            return true;
        } catch (Exception ex) {
            LOG.warn("[waitUntilClickable] Element located by {} was not clickable within {}s", by, timeout.toSeconds());
            return false;
        }
    }

    public void scrollIntoView(By by) {
        try {
            WebElement e = getWebElement(by);
            scrollIntoView(e);
            try { ExtentTestManager.logMessage(Status.INFO, "Scroll into view: " + by); } catch (Exception ignored) {}
        } catch (Exception ex) {
            LOG.debug("[scrollIntoView] Ignored for {}: {}", by, ex.toString());
        }
    }

    public boolean jsClick(By by) {
        try {
            WebElement e = getWebElement(by);
            boolean ok = jsClick(e);
            if (ok) {
                try { ExtentTestManager.logMessage(Status.PASS, "JS click on element " + by); } catch (Exception ignored) {}
            }
            return ok;
        } catch (Exception ex) {
            LOG.warn("[jsClick] Failed to click via JS for {}: {}", by, ex.toString());
            return false;
        }
    }

    public boolean verifyElementText(By by, String textValue) {
        WebElement e = getWebElement(by);
        boolean ok = verifyElementText(e, textValue);
        try {
            ExtentTestManager.logMessage(ok ? Status.PASS : Status.INFO,
                    (ok ? "Verify text equals PASSED for " : "Verify text equals for ") + by + " with value '" + textValue + "'");
        } catch (Exception ignored) {}
        return ok;
    }

    public boolean verifyElementTextContains(By by, String textValue) {
        WebElement e = getWebElement(by);
        boolean ok = e.getText().contains(textValue);
        try {
            ExtentTestManager.logMessage(ok ? Status.PASS : Status.INFO,
                    (ok ? "Verify text contains PASSED for " : "Verify text contains for ") + by + " with value '" + textValue + "'");
        } catch (Exception ignored) {}
        return ok;
    }

    // region Frame helpers
    public boolean switchToFrame(By by) {
        try {
            WebElement frame = getWebElement(by);
            new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
            try { ExtentTestManager.logMessage(Status.INFO, "Switched to frame: " + by); } catch (Exception ignored) {}
            return true;
        } catch (Exception ex) {
            LOG.warn("[switchToFrame] Failed to switch to frame {}: {}", by, ex.toString());
            return false;
        }
    }

    public void switchToDefaultContent() {
        try {
            DriverManager.getDriver().switchTo().defaultContent();
            try { ExtentTestManager.logMessage(Status.INFO, "Switched to default content"); } catch (Exception ignored) {}
        } catch (Exception ex) {
            LOG.debug("[switchToDefaultContent] Ignored: {}", ex.toString());
        }
    }
    // endregion
    // endregion

    public boolean verifyUrl(String url) {
        String current = DriverManager.getDriver().getCurrentUrl();
        LOG.info("Current URL: {}", current);
        LOG.info("Expected URL: {}", url);
        boolean ok = current.contains(url);
        try {
            ExtentTestManager.logMessage(ok ? Status.PASS : Status.INFO,
                    (ok ? "Verify current URL contains: " : "Current URL does not contain: ") + url);
        } catch (Exception ignored) {}
        return ok;
    }

    public void scrollToElement(WebElement e) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", e);
    }

    public void waitForPageLoaded() {
        // Best-effort waits: do not fail hard if site blocks JS/jQuery checks
        waitForJQueryLoaded();
        waitForJsLoaded();
    }

    public void waitForJQueryLoaded() {
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    Object active = ((JavascriptExecutor) driver).executeScript("return window.jQuery ? jQuery.active : 0");
                    if (active == null) return true; // treat as ready
                    if (active instanceof Number) {
                        return ((Number) active).longValue() == 0L;
                    }
                    return true;
                } catch (Exception e) {
                    // jQuery not present or JS disabled → don't block
                    return true;
                }
            }
        };

        try {
            wait = new WebDriverWait(DriverManager.getDriver(), pageLoadTimeout);
            wait.until(jQueryLoad);
        } catch (Throwable e) {
            LOG.warn("[waitForJQueryLoaded] Timeout waiting for jQuery to be idle. Continuing...", e);
            // Do not throw to avoid breaking tests on sites without jQuery
        }
    }

    public void waitForJsLoaded() {
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    String state = String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"));
                    // Consider 'interactive' acceptable on some apps doing late async loads
                    return "complete".equalsIgnoreCase(state) || "interactive".equalsIgnoreCase(state);
                } catch (Exception ex) {
                    // If JS execution is not possible, don't block
                    return true;
                }
            }
        };

        try {
            wait = new WebDriverWait(DriverManager.getDriver(), pageLoadTimeout);
            wait.until(jsLoad);
        } catch (Throwable e) {
            LOG.warn("[waitForJsLoaded] Timeout waiting for document.readyState to be complete/interactive. Continuing...", e);
            // Do not throw to reduce flakiness on pages that never reach 'complete'
        }
    }

    /**
     * đợi Angular tải xong với thời gian thiết lập sẵn
     */
    public void waitForAngularLoad() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), pageLoadTimeout, Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        final String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";

        //Wait for ANGULAR to load
        ExpectedCondition<Boolean> angularLoad = driver -> {
            assert driver != null;
            return Boolean.valueOf(((JavascriptExecutor) DriverManager.getDriver())
                    .executeScript(angularReadyScript).toString());
        };

        //Get Angular is Ready
        boolean angularReady = Boolean.parseBoolean(js.executeScript(angularReadyScript).toString());

        //Wait ANGULAR until it is Ready!
        if (!angularReady) {
            LOG.warn("Angular is NOT Ready!");
            //Wait for Angular to load
            try {
                //Wait for jQuery to load
                wait = new WebDriverWait(DriverManager.getDriver(), pageLoadTimeout);

                wait.until(angularLoad);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for Angular load. (" + pageLoadTimeout + "s)");
            }
        }

    }

    private String safeElementToString(WebElement e) {
        try {
            return String.valueOf(e);
        } catch (Exception ex) {
            return "<element>";
        }
    }
}
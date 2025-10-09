package com.an.pages;

import com.an.common.BasePage;
import com.an.helpers.SystemHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerPage extends BasePage {

    // Customer Page Elements
    private By addNewCustomerBtn = By.cssSelector("a[class*='test']");
    private By customerSearchInput = By.xpath("//div[@id='clients_filter']//input[@placeholder='Search...']");
    // Always target the newest (last) row because table is sorted old -> new
    private By LastFoundItemCustomerName = By.xpath("//tbody/tr[last()]/td[3]/a");

    // Customer Form Elements
    private By companyInput = By.id("company");
    private By vatInput = By.id("vat");
    private By phoneNumberInput = By.id("phonenumber");
    private By websiteInput = By.id("website");
    private By groupDropdown = By.cssSelector("button[data-id='groups_in\\[\\]']");
    private By groupSearchInput = By.cssSelector("div.input-group-btn input[aria-label='Search']");
    private By languageSelection = By.xpath("//button[@data-id='default_language']");
    private By vietnameseItem = By.xpath("//span[normalize-space()='Vietnamese']");
    private By addressInput = By.xpath("//textarea[@id='address']");
    private By cityInput = By.xpath("//input[@id='city']");
    private By stateInput = By.xpath("//input[@id='state']");
    private By zipInput = By.xpath("//input[@id='zip']");
    private By countrySelection = By.xpath("//button[@data-id='country']");
    private By countryInput = By.xpath("//button[@data-id='country']/following-sibling::div//input");
    private By saveBtn = By.xpath("//div[@id='profile-save-section']//button[normalize-space()='Save']");
    private By alertMessage = By.xpath("//span[@class='alert-title']");
    private By browseBtn = By.cssSelector("#client-attachments-upload");

    // customer details page elements
    private By fileUploadTab = By.cssSelector("a[data-group='attachments']");

    public CustomerPage() {
        super();
    }

    public boolean isAt() { return webElementHelper.verifyUrl("clients"); }

    public boolean addNewCustomerClick() {
        webElementHelper.clickElement(addNewCustomerBtn);
        return webElementHelper.verifyUrl("client");
    }

    private void customerDataInput(String companyName) {
        webElementHelper.setText(companyInput, companyName);
        webElementHelper.setText(vatInput, "123456789");
        webElementHelper.setText(phoneNumberInput, "0901234567");
        webElementHelper.setText(websiteInput, "www.example.com");
        webElementHelper.clickElement(groupDropdown);
        webElementHelper.setText(groupSearchInput, "a");
        webElementHelper.setKey(groupSearchInput, Keys.ENTER);
        webElementHelper.clickElement(groupDropdown);

        webElementHelper.clickElement(languageSelection);
        webElementHelper.clickElement(vietnameseItem);
//        webElementHelper.delayFor(10);

        webElementHelper.setText(addressInput, "122/22/112");
        webElementHelper.setText(cityInput, "City");
        webElementHelper.setText(stateInput, "State");
        webElementHelper.setText(zipInput, "12345");

        webElementHelper.clickElement(countrySelection);
        webElementHelper.setText(countryInput, "Vietnam");
        webElementHelper.setKey(countryInput, Keys.ENTER);
    }

    public boolean createCustomer(String companyName) {
        customerDataInput(companyName);
        webElementHelper.clickElement(saveBtn);
        webElementHelper.delayFor(1);
        return webElementHelper.verifyElementText(alertMessage, "Customer added successfully.");
    }

    public boolean searchCustomer(String companyName) {
        webElementHelper.waitForPageLoaded();
        webElementHelper.clickElement(menuCustomers);
        webElementHelper.waitForPageLoaded();
        webElementHelper.setText(customerSearchInput, companyName);
        webElementHelper.waitForPageLoaded();
        webElementHelper.delayFor(2);

        return webElementHelper.verifyElementText(LastFoundItemCustomerName, companyName);
    }

    public boolean uploadFile() {
        // Ensure the first customer link is clickable to avoid stale/no such element
        boolean clickable = webElementHelper.waitUntilClickable(LastFoundItemCustomerName, Duration.ofSeconds(10));
        if (!clickable) return false;
        webElementHelper.clickElement(LastFoundItemCustomerName);
        webElementHelper.waitForPageLoaded();
        webElementHelper.clickElement(fileUploadTab);
        webElementHelper.waitForPageLoaded();

        // Build absolute path to test file inside repo
        String absolutePath;
        try {
            java.io.File f = new java.io.File(SystemHelper.getCurrentDir() + "src\\test\\java\\com\\an\\dataset\\uploadFileTesting.txt");
            absolutePath = f.getAbsolutePath();
        } catch (Exception ex) {
            absolutePath = SystemHelper.getCurrentDir() + "src\\test\\java\\com\\an\\dataset\\uploadFileTesting.txt";
        }

        // Prefer direct sendKeys to the file input if possible (more reliable than Robot/dialog)
        try {
            org.openqa.selenium.WebElement fileInput = webElementHelper.getWebElement(browseBtn);
            fileInput.sendKeys(absolutePath);
        } catch (Exception e) {
            // Fallback: open OS file dialog and use Robot to paste path
            webElementHelper.clickElement(browseBtn); // Mở form select file từ local PC

            Robot rb = null;
            try {
                rb = new Robot();
            } catch (AWTException awt) {
                awt.printStackTrace();
            }

            if (rb != null) {
                StringSelection str = new StringSelection(absolutePath);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

                webElementHelper.delayFor(1);

                rb.keyPress(KeyEvent.VK_CONTROL);
                rb.keyPress(KeyEvent.VK_V);
                rb.keyRelease(KeyEvent.VK_CONTROL);
                rb.keyRelease(KeyEvent.VK_V);

                webElementHelper.delayFor(1);

                rb.keyPress(KeyEvent.VK_ENTER);
                rb.keyRelease(KeyEvent.VK_ENTER);
            }
        }

        webElementHelper.delayFor(3);
        return verifyFileUploadSuccess();
    }

    private boolean verifyFileUploadSuccess() {
        webElementHelper.waitForPageLoaded();
        // Đợi link tên file xuất hiện trong bảng
        By fileNameLink = By.xpath("//table[@id='DataTables_Table_0']//a[normalize-space()='uploadFileTesting.txt']");
        long end = System.currentTimeMillis() + 12000; // tối đa ~12s
        while (System.currentTimeMillis() < end) {
            try {
                java.util.List<org.openqa.selenium.WebElement> links = webElementHelper.getWebElements(fileNameLink);
                if (links != null && !links.isEmpty()) {
                    return true;
                }
            } catch (Exception ignored) {}
            webElementHelper.delayFor(1);
        }
        return false;
    }

}

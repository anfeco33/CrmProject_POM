package com.an.pages;

import com.an.common.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class ProjectPage extends BasePage {

    private By createNewProjectBtn = By.cssSelector(".btn.btn-primary.pull-left.display-block.mright5");

    private By projectNameInput = By.id("name");
    private By customerSelection = By.cssSelector("button[class='btn dropdown-toggle bs-placeholder btn-default'] div[class='filter-option-inner-inner']");
    private By customerSearchInput = By.cssSelector("input[aria-controls='bs-select-6']");
    private By firstCustomerFoundItem = By.cssSelector("div[id*='bs-select-6'] ul[role='presentation']"); // Chỉ khi so sánh đúng với tên input
    private By billingTypeSelection = By.cssSelector("button[data-id='billing_type']");
    private By projectHoursBillingTypeItem = By.id("bs-select-1-2");
    private By statusSelection = By.cssSelector("button[title='In Progress']");
    private By notStartedStatusItem = By.id("bs-select-2-0");
    private By ratePerHourInput = By.xpath("//input[@id='project_rate_per_hour']");
    private By estimatedHoursInput = By.id("estimated_hours");
    private By deadlineDateInput = By.id("deadline");
    private By datePickerInput = By.cssSelector("td.xdsoft_current");
    private By tagsInput = By.cssSelector("input[placeholder='Tag']"); // Nhập 'selenium' + enter
    private By descriptionIframe = By.id("description_ifr");
    private By descriptionBody = By.id("tinymce");

    // Project Settings
    private By projectSettingsTab = By.cssSelector("a[role='tab'][href='#tab_settings']");
    private By sendContactNotificationsSetting = By.cssSelector("button[title='To all contacts with notifications for projects enabled']");
    private By doNotSendNotificationsItem = By.id("bs-select-4-2");

    private By saveBtn = By.cssSelector("button[type='submit']");

    private By successfulVerificationTitle = By.cssSelector("button[data-id$='project_top']");

    public ProjectPage() {
        super();
    }

    public boolean isAt() { return webElementHelper.verifyUrl("projects"); }

    public boolean createNewProject(String projectName, String customerName, String ratePerHour, String estimatedHours, String description) throws Exception {
        addNewProjectClick();
        projectDataInput(projectName, customerName, ratePerHour, estimatedHours, description);
        setProjectSettingsTab();
        webElementHelper.clickElement(saveBtn);

        webElementHelper.waitForPageLoaded();
        return webElementHelper.verifyElementTextContains(successfulVerificationTitle, projectName);
    }

    public boolean addNewProjectClick() {
        webElementHelper.clickElement(createNewProjectBtn);
        return webElementHelper.verifyUrl("project");
    }

    public void projectDataInput(String projectName, String customerName, String ratePerHour, String estimatedHours, String description) throws Exception{
        webElementHelper.setText(projectNameInput, projectName);
        webElementHelper.clickElement(customerSelection);
        webElementHelper.setText(customerSearchInput, customerName);
        webElementHelper.delayFor(1);
        webElementHelper.verifyElementDisplayed(firstCustomerFoundItem);
        // Raise Error when not found
        // contain text
        if (!webElementHelper.verifyElementTextContains(firstCustomerFoundItem, customerName)) {
            throw new RuntimeException("Customer not found in project creation: " + customerName);
        }

        webElementHelper.clickElement(firstCustomerFoundItem);
        webElementHelper.clickElement(billingTypeSelection);
        webElementHelper.clickElement(projectHoursBillingTypeItem);
        webElementHelper.clickElement(statusSelection);
        webElementHelper.clickElement(notStartedStatusItem);
        webElementHelper.clickElement(ratePerHourInput);
//        webElementHelper.delayFor(2);
        webElementHelper.setText(ratePerHourInput, ratePerHour);
        webElementHelper.clickElement(estimatedHoursInput);
        webElementHelper.setText(estimatedHoursInput, estimatedHours);
        webElementHelper.clickElement(deadlineDateInput);
//        webElementHelper.delayFor(2);
        webElementHelper.clickElement(datePickerInput);
        webElementHelper.setText(tagsInput, "selenium");
        webElementHelper.setKey(tagsInput, Keys.ENTER);

        // Switch into TinyMCE iframe to type description
        if (webElementHelper.switchToFrame(descriptionIframe)) {
            webElementHelper.setText(descriptionBody, description);
            webElementHelper.switchToDefaultContent();
        }
    }

    private void setProjectSettingsTab() {
        webElementHelper.clickElement(projectSettingsTab);
        webElementHelper.clickElement(sendContactNotificationsSetting);
        webElementHelper.clickElement(doNotSendNotificationsItem);
    }
}

package com.an.pages;

import com.an.common.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class DashboardPage extends BasePage {

    @FindBy(xpath = "//a[@href='https://crm.anhtester.com/admin/todo' and contains(text(),'View All')]")
    private WebElement viewAllLink;

    @FindBy(xpath = "//a[normalize-space()='New To Do']")
    private WebElement newToDoBtn;

    @FindBy(xpath = "//h4[@id='myModalLabel']")
    private WebElement headerToDoModal;

    @FindBy(id = "description")
    private WebElement descriptionInput;

    @FindBy(xpath = "//button[normalize-space()='Save']")
    private WebElement saveBtn;

    private By successfulMessage = By.cssSelector(".alert-success");

    public DashboardPage() { super(); }

    public boolean isAt() { return webElementHelper.verifyUrl("https://crm.anhtester.com/admin/"); }

    public boolean viewAllClickable() { return webElementHelper.verifyElementDisplayed(viewAllLink) && viewAllLink.isEnabled(); }

    public boolean viewAllClick() {
        if (viewAllClickable()) {
            webElementHelper.clickElement(viewAllLink);
        }
        return webElementHelper.verifyUrl("/admin/todo");
    }

    public boolean newToDoClickable() {
        return webElementHelper.verifyElementDisplayed(newToDoBtn) && newToDoBtn.isEnabled();
    }

    public boolean newToDoClick() {
        if (!webElementHelper.verifyElementDisplayed(newToDoBtn)) return false;
        webElementHelper.clickElement(newToDoBtn);
        return webElementHelper.waitUntilVisible(headerToDoModal, Duration.ofSeconds(15));
    }

    public boolean createToDo(String description) {
        menuDashboardClick();
        newToDoClick();
        webElementHelper.setText(descriptionInput, description);
        webElementHelper.clickElement(saveBtn);
        return webElementHelper.verifyElementDisplayed(successfulMessage);
    }

}

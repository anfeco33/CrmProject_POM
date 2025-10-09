package com.an.pages;

import com.an.common.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//h1[normalize-space()='Login']")
    private WebElement headerPageText;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginBtn;

    public LoginPage() {
        super();
    }

    // Sau khi thực hiện click Submit thì khởi tạo trang DashboardPage
    public DashboardPage login(String email, String password) throws Exception {
        webElementHelper.waitForPageLoaded();
        webElementHelper.setText(emailInput, email);
        webElementHelper.setText(passwordInput, password);
        webElementHelper.clickElement(loginBtn);
        webElementHelper.delayFor(2);
        return new DashboardPage();
    }

    public boolean isAt() {
        boolean headerVisible = webElementHelper.waitUntilVisible(headerPageText, Duration.ofSeconds(5));
        boolean urlLooksLikeLogin = webElementHelper.verifyUrl("/authentication");
        return headerVisible || urlLooksLikeLogin;
    }
}

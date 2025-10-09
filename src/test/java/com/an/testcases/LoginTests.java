package com.an.testcases;

import com.an.common.TestBase;
import com.an.dataprovider.DataProviderFactory;
import com.an.helpers.CaptureHelper;
import com.an.helpers.PropertiesHelper;
import org.testng.Assert;
import org.testng.annotations.Test;

// pages
import com.an.pages.DashboardPage;

import java.lang.reflect.Method;
import java.util.Hashtable;

public class LoginTests extends TestBase {

//    @DataProvider(name = "loginDataByDataProvider", parallel = true)
//    public Object[][] loginData() {
//        return new Object[][] {
//                // email, password, expectedResult (passed/failed)
//                {"client01@mailinator.com", "123456", "failed"},
//                {PropertiesHelper.getValue("email"), PropertiesHelper.getValue("password") , "passed"},
//                {"ad@mailinator.com", "123456", "failed"},
//                {"admin_example", "123456", "failed"},
//        };
//    }

//    @Test(dataProvider = "data_provider_01", dataProviderClass = DataProviderFactory.class)
//    public void testLogin(String email, String password, String expectedResult) throws Exception {
//        DashboardPage dashboard = loginPage.login(email, password);
//        String normalized = expectedResult == null ? "" : expectedResult.trim().toLowerCase();
//        switch (normalized) {
//            case "passed":
//                Assert.assertTrue(
//                        dashboard.isAt(),
//                        "Should be at Dashboard after successful login"
//                );
//                break;
//            case "failed":
//                Assert.assertTrue(
//                        loginPage.isAt(),
//                        "Should remain on Login page after failed login"
//                );
//                break;
//            default:
//                org.testng.Assert.fail("Unexpected expectedResult value: '" + expectedResult + "'. Use 'passed' or 'failed'.");
//        }
//    }

    @Test(priority = 1, dataProvider = "data_provider_01", dataProviderClass = DataProviderFactory.class)
    public void testLoginFromDataProviderExcelHashtable(Hashtable< String, String > data, Method method) throws Exception {
//        CaptureHelper.startRecord(method.getName());

        DashboardPage dashboard = loginPage.login(data.get("username"), data.get("password"));
        String normalized = data.get("result") == null ? "" : data.get("result").trim().toLowerCase();
        switch (normalized) {
            case "passed":
                Assert.assertTrue(
                        dashboard.isAt(),
                        "Should be at Dashboard after successful login"
                );
                break;
            case "failed":
                Assert.assertTrue(
                        loginPage.isAt(),
                        "Should remain on Login page after failed login"
                );
                break;
            default:
                org.testng.Assert.fail("Unexpected expectedResult value: '" + data.get("result") + "'. Use 'passed' or 'failed'.");
        }
    }

    // Test login using Faker-generated credentials (expected to fail and remain on Login page)
    @Test(priority = 2, dataProvider = "data_provider_login_faker", dataProviderClass = DataProviderFactory.class)
    public void testLoginWithFakerInvalid(String email, String password) throws Exception {
        DashboardPage dashboard = loginPage.login(email, password);
        Assert.assertTrue(
                loginPage.isAt(),
                "With random invalid credentials, the app should remain on the Login page"
        );
    }

}

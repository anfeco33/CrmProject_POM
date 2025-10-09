package com.an.dataprovider;

import com.an.helpers.DataFakerHelper;
import com.an.helpers.ExcelHelper;
import com.an.helpers.PropertiesHelper;
import com.an.helpers.SystemHelper;
import net.datafaker.Faker;
import org.testng.annotations.DataProvider;

public class DataProviderFactory {

    @DataProvider(name = "data_provider_01")
    public Object[][] dataHRM() {
        ExcelHelper excelHelper = new ExcelHelper();
        // Update the path to match the actual repository structure
        String path = SystemHelper.getCurrentDir() + "src\\test\\java\\com\\an\\dataset\\loginData.xlsx";
        Object[][] data = excelHelper.getDataHashTable(path, "Sheet1", 1, 3);
        System.out.println("Login Data from Excel loaded from: " + path);
        return data;
    }

    @DataProvider(name = "data_provider_02", parallel = true)
    public Object[][] dataLogin() {
        return new Object[][] {
                // email, password, expectedResult (passed/failed)
                {"client01@mailinator.com", "123456", "failed"},
                {PropertiesHelper.getValue("email"), PropertiesHelper.getValue("password") , "passed"},
                {"ad@mailinator.com", "123456", "failed"},
                {"admin_example", "123456", "failed"},
        };
    }

    @DataProvider(name = "data_provider_faker", parallel = true)
    public Object[][] dataFakerCustomers() {
        Faker faker = DataFakerHelper.getFaker();
        int rows = 3; // generate 3 sets of data by default
        Object[][] data = new Object[rows][3];
        for (int i = 0; i < rows; i++) {
            String company = faker.company().name();
            String email = faker.internet().emailAddress();
            String phone = faker.phoneNumber().cellPhone();
            data[i][0] = company;
            data[i][1] = email;
            data[i][2] = phone;
        }
        return data;
    }

    // Faker-based login data (negative cases): random email/password pairs
    @DataProvider(name = "data_provider_login_faker", parallel = true)
    public Object[][] dataFakerLogin() {
        Faker faker = DataFakerHelper.getFaker();
        int rows = 5;
        Object[][] data = new Object[rows][2];
        for (int i = 0; i < rows; i++) {
            String email = faker.internet().emailAddress();
            // Mix of random passwords with varying lengths/characters
            String password = faker.internet().password(6, 14, true, true, true);
            data[i][0] = email;
            data[i][1] = password;
        }
        return data;
    }
    
}

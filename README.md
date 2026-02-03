# CrmProject_POM - AnhTester CRM Test Automation Framework

A comprehensive Selenium WebDriver test automation framework for testing the AnhTester CRM system using the Page Object Model (POM) design pattern.

## 🎯 Project Overview

This project provides automated testing for the AnhTester CRM application, implementing best practices in test automation including:
- Page Object Model (POM) design pattern
- Selenium WebDriver 4
- TestNG framework
- Extent Reports for test reporting
- WebDriverManager for automatic driver management

## 📋 Prerequisites

- Java JDK 11 or higher
- Maven 3.6 or higher
- Chrome/Firefox/Edge browser installed

## 🚀 Getting Started

### Clone the Repository
```bash
git clone https://github.com/anfeco33/CrmProject_POM.git
cd CrmProject_POM
```

### Install Dependencies
```bash
mvn clean install
```

## 🏗️ Project Structure

```
CrmProject_POM/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── anhtester/
│   │               ├── base/          # Base classes
│   │               │   ├── BasePage.java
│   │               │   └── BaseTest.java
│   │               ├── pages/         # Page Object classes
│   │               │   ├── LoginPage.java
│   │               │   ├── DashboardPage.java
│   │               │   ├── CustomersPage.java
│   │               │   ├── ProjectsPage.java
│   │               │   └── TasksPage.java
│   │               └── utils/         # Utility classes
│   │                   ├── TestListener.java
│   │                   └── ScreenshotUtil.java
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── anhtester/
│       │           └── tests/         # Test classes
│       │               ├── LoginTest.java
│       │               ├── CustomerTest.java
│       │               ├── ProjectTest.java
│       │               └── TaskTest.java
│       └── resources/
│           ├── config.properties      # Configuration file
│           └── testng.xml            # TestNG suite file
├── pom.xml                           # Maven configuration
└── README.md
```

## ⚙️ Configuration

Edit `src/test/resources/config.properties` to configure:

```properties
# Application URL
app.url=https://crm.anhtester.com/admin/authentication

# Browser Configuration
browser=chrome          # Options: chrome, firefox, edge
headless=false         # Set to true for headless execution

# Timeouts
implicit.wait=10
explicit.wait=20
page.load.timeout=30

# Test Credentials
username=admin@example.com
password=123456
```

## 🧪 Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=LoginTest
```

### Run Tests in Headless Mode
```bash
mvn test -Dbrowser=chrome -Dheadless=true
```

### Run with Different Browser
```bash
mvn test -Dbrowser=firefox
```

## 📊 Test Reports

After test execution, reports are generated in:
- **Extent Reports**: `test-output/reports/TestReport_[timestamp].html`
- **TestNG Reports**: `test-output/index.html`
- **Screenshots**: `test-output/screenshots/` (captured on test failures)

## 🧩 Test Modules

### 1. Login Tests
- Valid login credentials
- Invalid email validation
- Invalid password validation
- Empty credentials validation

### 2. Customer Tests
- Customer page accessibility
- Create new customer
- Search customer functionality

### 3. Project Tests
- Project page accessibility
- Create new project
- Search project functionality

### 4. Task Tests
- Task page accessibility
- Create new task
- Search task functionality

## 🔧 Key Features

- **Page Object Model**: Clean separation between test logic and page interactions
- **Reusable Components**: Base classes for common functionality
- **Automatic Driver Management**: WebDriverManager handles browser drivers
- **Comprehensive Reporting**: Extent Reports with screenshots on failures
- **Flexible Configuration**: Easy configuration through properties file
- **Cross-browser Support**: Chrome, Firefox, and Edge
- **Parallel Execution**: TestNG parallel test execution support

## 📝 Adding New Tests

1. Create a new Page Object in `src/main/java/com/anhtester/pages/`
2. Create corresponding test class in `src/test/java/com/anhtester/tests/`
3. Add test class to `src/test/resources/testng.xml`

Example:
```java
public class NewPage extends BasePage {
    public NewPage(WebDriver driver) {
        super(driver);
    }
    // Add page methods
}
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is open source and available for educational purposes.

## 👥 Author

AnhTester Team

## 📞 Support

For issues and questions, please create an issue in the GitHub repository.

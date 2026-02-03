# Quick Start Guide

## Installation and Setup

### 1. Prerequisites
Ensure you have the following installed:
- Java JDK 11+
- Maven 3.6+
- Git

### 2. Clone the Repository
```bash
git clone https://github.com/anfeco33/CrmProject_POM.git
cd CrmProject_POM
```

### 3. Install Dependencies
```bash
mvn clean install -DskipTests
```

## Configuration

### Update Test Credentials
Edit `src/test/resources/config.properties` with your credentials:

```properties
# Test Credentials
username=your_username@example.com
password=your_password
```

### Browser Configuration
You can configure the browser in `config.properties`:

```properties
browser=chrome    # Options: chrome, firefox, edge
headless=false    # Set to true for CI/CD pipelines
```

## Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
# Run Login Tests
mvn test -Dtest=LoginTest

# Run Customer Tests
mvn test -Dtest=CustomerTest

# Run Project Tests
mvn test -Dtest=ProjectTest

# Run Task Tests
mvn test -Dtest=TaskTest
```

### Run with Different Browsers
```bash
# Firefox
mvn test -Dbrowser=firefox

# Edge
mvn test -Dbrowser=edge

# Chrome Headless
mvn test -Dbrowser=chrome -Dheadless=true
```

### Run Specific Test Method
```bash
mvn test -Dtest=LoginTest#testLoginWithValidCredentials
```

## Test Reports

After running tests, find reports at:

### Extent Reports
Open `test-output/reports/TestReport_[timestamp].html` in a browser for detailed HTML reports with:
- Test execution summary
- Pass/Fail status
- Screenshots on failures
- Execution time
- System information

### TestNG Reports
Open `test-output/index.html` for standard TestNG reports

### Screenshots
Failed test screenshots are saved in: `test-output/screenshots/`

## Project Structure Overview

```
CrmProject_POM/
├── src/
│   ├── main/java/com/anhtester/
│   │   ├── base/              # Base classes for tests and pages
│   │   ├── pages/             # Page Object Model classes
│   │   └── utils/             # Utility classes
│   └── test/
│       ├── java/com/anhtester/tests/  # Test classes
│       └── resources/                  # Configuration files
├── pom.xml                    # Maven dependencies
└── README.md                  # Documentation
```

## Adding New Tests

### 1. Create a Page Object
Create a new class in `src/main/java/com/anhtester/pages/`:

```java
package com.anhtester.pages;

import com.anhtester.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewPage extends BasePage {
    
    @FindBy(id = "elementId")
    private WebElement element;
    
    public NewPage(WebDriver driver) {
        super(driver);
    }
    
    public void performAction() {
        click(element);
    }
}
```

### 2. Create a Test Class
Create a test in `src/test/java/com/anhtester/tests/`:

```java
package com.anhtester.tests;

import com.anhtester.base.BaseTest;
import com.anhtester.pages.NewPage;
import org.testng.annotations.Test;

public class NewTest extends BaseTest {
    
    @Test
    public void testNewFeature() {
        NewPage page = new NewPage(driver);
        page.performAction();
        // Add assertions
    }
}
```

### 3. Add Test to Suite
Update `src/test/resources/testng.xml`:

```xml
<test name="New Tests">
    <classes>
        <class name="com.anhtester.tests.NewTest"/>
    </classes>
</test>
```

## Troubleshooting

### Issue: WebDriver not found
**Solution**: WebDriverManager automatically downloads drivers. Ensure internet connection.

### Issue: Tests fail with timeout
**Solution**: Increase timeouts in `config.properties`:
```properties
implicit.wait=20
explicit.wait=30
page.load.timeout=60
```

### Issue: Element not found
**Solution**: 
1. Check if page is fully loaded
2. Verify locator strategy
3. Increase wait time
4. Use explicit waits in page methods

## Best Practices

1. **Use Page Object Model**: Keep page logic separate from test logic
2. **Reuse Base Classes**: Extend BasePage and BaseTest for common functionality
3. **Wait Strategies**: Always wait for elements before interaction
4. **Clear Test Data**: Use unique identifiers (timestamps) for test data
5. **Assertions**: Add meaningful assertions to validate functionality
6. **Test Independence**: Each test should be independent and self-contained
7. **Logging**: Use proper logging for debugging

## CI/CD Integration

### GitHub Actions Example
Create `.github/workflows/test.yml`:

```yaml
name: Test Execution

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
        
    - name: Run tests
      run: mvn clean test -Dheadless=true
      
    - name: Upload Reports
      uses: actions/upload-artifact@v2
      if: always()
      with:
        name: test-reports
        path: test-output/
```

## Support

For issues or questions:
- Create an issue on GitHub
- Check documentation in README.md
- Review test examples in the repository

## License

This project is available for educational and testing purposes.

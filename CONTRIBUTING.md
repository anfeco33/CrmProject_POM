# Contributing to CrmProject_POM

Thank you for your interest in contributing to the AnhTester CRM Testing Framework! This document provides guidelines for contributing to the project.

## Table of Contents
- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Testing Guidelines](#testing-guidelines)
- [Pull Request Process](#pull-request-process)

## Code of Conduct

- Be respectful and inclusive
- Provide constructive feedback
- Focus on what is best for the community
- Show empathy towards other community members

## Getting Started

### 1. Fork the Repository
Click the "Fork" button at the top right of the repository page.

### 2. Clone Your Fork
```bash
git clone https://github.com/YOUR_USERNAME/CrmProject_POM.git
cd CrmProject_POM
```

### 3. Add Upstream Remote
```bash
git remote add upstream https://github.com/anfeco33/CrmProject_POM.git
```

### 4. Create a Branch
```bash
git checkout -b feature/your-feature-name
```

## Development Workflow

### 1. Keep Your Fork Updated
```bash
git fetch upstream
git checkout main
git merge upstream/main
```

### 2. Make Your Changes
- Write clean, readable code
- Follow existing code structure
- Add comments for complex logic
- Update documentation as needed

### 3. Test Your Changes
```bash
# Compile the project
mvn clean compile

# Run all tests
mvn clean test

# Run specific test
mvn test -Dtest=YourTestClass
```

### 4. Commit Your Changes
```bash
git add .
git commit -m "feat: add descriptive commit message"
```

**Commit Message Format:**
- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `test:` - Adding or updating tests
- `refactor:` - Code refactoring
- `style:` - Code style changes
- `chore:` - Maintenance tasks

### 5. Push to Your Fork
```bash
git push origin feature/your-feature-name
```

## Coding Standards

### Java Code Style

#### Naming Conventions
- **Classes**: PascalCase (e.g., `LoginPage`, `CustomerTest`)
- **Methods**: camelCase (e.g., `clickLoginButton`, `enterEmail`)
- **Variables**: camelCase (e.g., `emailInput`, `passwordField`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_WAIT_TIME`)

#### File Organization
```java
package declaration
↓
imports (grouped and sorted)
↓
class declaration
↓
instance variables
↓
constructor
↓
public methods
↓
private/protected methods
```

#### Method Structure
```java
/**
 * Brief description of what the method does
 * @param paramName Description of parameter
 * @return Description of return value
 */
public ReturnType methodName(ParamType paramName) {
    // Method implementation
}
```

### Page Object Guidelines

1. **Locator Strategy Priority**:
   - id (most reliable)
   - name
   - css selector
   - xpath (least reliable, last resort)

2. **Page Object Structure**:
```java
public class PageName extends BasePage {
    
    // Locators section
    @FindBy(id = "elementId")
    private WebElement element;
    
    // Constructor
    public PageName(WebDriver driver) {
        super(driver);
    }
    
    // Action methods
    public void performAction() {
        click(element);
    }
    
    // Verification methods
    public boolean isElementVisible() {
        return isDisplayed(element);
    }
}
```

### Test Class Guidelines

1. **Test Structure**:
```java
public class TestName extends BaseTest {
    
    @Test(priority = 1, description = "Test description")
    public void testMethodName() {
        // Arrange - Set up test data
        
        // Act - Perform actions
        
        // Assert - Verify results
    }
}
```

2. **Test Naming**: Use descriptive names that explain what is being tested
   - Good: `testLoginWithValidCredentials`
   - Bad: `test1`, `loginTest`

3. **Assertions**: Always include meaningful assertions
```java
Assert.assertTrue(page.isDisplayed(), "Page should be displayed after login");
```

## Testing Guidelines

### Before Submitting

1. **Run All Tests**:
```bash
mvn clean test
```

2. **Check Code Compilation**:
```bash
mvn clean compile
```

3. **Verify No Breaking Changes**:
   - Ensure existing tests still pass
   - Test new functionality thoroughly

### Writing Tests

1. **Test Independence**: Tests should not depend on each other
2. **Data Cleanup**: Clean up test data after execution
3. **Use Test Data**: Don't hardcode sensitive data
4. **Add Comments**: Explain complex test logic

### Test Coverage

When adding new features:
- Add at least one positive test case
- Add negative test cases
- Test edge cases
- Test error handling

## Pull Request Process

### 1. Create a Pull Request
- Go to your fork on GitHub
- Click "New Pull Request"
- Select your feature branch
- Fill in the PR template

### 2. PR Title Format
```
[Type] Brief description

Types: Feature, Bugfix, Enhancement, Documentation, Test
Example: [Feature] Add customer management tests
```

### 3. PR Description Template
```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Enhancement
- [ ] Documentation update
- [ ] Test addition/update

## Changes Made
- Change 1
- Change 2
- Change 3

## Testing Done
- Test scenario 1
- Test scenario 2

## Screenshots (if applicable)
Add screenshots for UI changes

## Checklist
- [ ] Code follows project style guidelines
- [ ] Tests added/updated
- [ ] All tests pass
- [ ] Documentation updated
- [ ] No breaking changes
```

### 4. Review Process
- Wait for maintainer review
- Address review comments
- Make requested changes
- Push updates to your branch

### 5. After Approval
- PR will be merged by maintainers
- Delete your feature branch
- Update your local main branch

## Areas for Contribution

### High Priority
- Additional test scenarios
- Cross-browser testing improvements
- Test data management
- Parallel execution optimization

### Medium Priority
- Documentation improvements
- Code refactoring
- Performance improvements
- Additional utility methods

### Low Priority
- Code comments
- Example tests
- Test reports enhancement

## Questions?

If you have questions:
1. Check existing documentation
2. Search existing issues
3. Create a new issue with "Question" label
4. Tag maintainers if urgent

## License

By contributing, you agree that your contributions will be licensed under the same license as the project.

Thank you for contributing to CrmProject_POM! 🎉

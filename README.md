# CRM Test Automation (POM)
This project is an automation test suite for a CRM web application using the Page Object Model design pattern. The goal is to keep the structure clean and maintainable, with clear reports and execution evidence (screenshots/videos).

## Tech Stack & Libraries
- **Language & Build**: Java 17, Maven
- **Test Framework**: TestNG, Maven Surefire Plugin
- **UI Automation**: Selenium WebDriver 4, WebDriverManager (auto driver management)
- **Reporting**: ExtentReports (HTML report)
- **Data & Utilities**: Apache POI (Excel), DataFaker (data generation), Apache Commons IO
- **Image Capture**: Selenium TakesScreenshot + custom helper (`CaptureHelper`)
- **Video Capture**: Monte Media (`monte-screen-recorder`)

## Architecture Overview
- Page Object Model (separating pages and actions)
- TestNG Listeners (logging, attaching screenshots on failure, initializing reports)
- ThreadLocal WebDriver (safe parallel execution)
- Multi-browser support: Chrome, Firefox, Edge

## Test Artifacts (Execution Evidence)
- **Report**: `test-output/ExtentReport.html`
- **Screenshots**: `test-output/screenshots`
- **Screen Recordings**: `test-output/screen-records`

## Quick Start
```bash
mvn clean test
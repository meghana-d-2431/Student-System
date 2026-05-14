package studentsystem.studentsystem;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StudentsystemApplicationSeleniumTest {

    // @Test
    // void openStudentFrontendPage() throws InterruptedException {

    //     // WebDriver is a Selenium interface for controlling web browsers. Here we use ChromeDriver to control the Chrome browser.
    //     WebDriver driver = new ChromeDriver(); // open Chrome browser

    //     try {
    //         // get() method is used to navigate to a specified URL.
    //         driver.get("http://localhost:5173"); // navigate to the frontend URL

    //         Thread.sleep(2000); // wait for 2 seconds to allow the page to load completely
    //         // getPageSource() method returns the source code of the current page as a string. We can use this to verify if the page has loaded correctly.
    //         String pageSource = driver.getPageSource();//  verify if the page is loaded

    //         // The assertTrue() method is used to assert that a condition is true. 
    //         // pageSource is a string containing the HTML and pageSource.length() gives the length of that string. If the length is greater than 0, it means the page source is not empty, indicating that the page has loaded successfully.
    //         assertTrue(pageSource.length() > 0); // check if the page source is not empty and see HTML content

    //         System.out.println("Frontend page opened successfully");// print success message

    //     } finally {
    //         driver.quit();// close the browser
    //     }
    // } // example -1 

    private WebDriver driver;
    private WebDriverWait wait; // use for waiting for specific conditions to be met before proceeding with the test execution, 
    // such as waiting for an element to be visible or clickable. 

    @BeforeEach // @BeforeEach, which means it will be executed before each test method in the class.
    void setUp() {
        ChromeOptions options = new ChromeOptions();

        // use this when you do not want browser window to open
        // options.addArguments("--headless=new");

        driver = new ChromeDriver(options); // open the chrome browser with the specified options like headless mode, which allows you to run tests without opening a browser window.
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // initialize the WebDriverWait with a timeout of 10 seconds, 
        // which means it will wait up to 10 seconds for a specific condition to be met before throwing an exception.
    }

    @Test
    void addStudentAndVerifyStudentIsDisplayed() { // add a student through the frontend and verify that the student is displayed on the page.
        driver.get("http://localhost:5173");

        String studentName = "Selenium Student " + System.currentTimeMillis(); // generate a unique student name using the current timestamp to ensure that each test run adds a distinct student, 
        String studentAddress = "Texas";

        // wait.until() is used to wait for a specific condition to be met before proceeding with the test execution. In this case, it waits until the element located by the CSS selector "[data-testid='student-name-input']" is visible on the page.
        WebElement nameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-testid='student-name-input']")
                )
        );

        // findElement() method is used to locate an element on the web page using a specified locator strategy. In this case, it locates the element using a CSS selector that targets an element with the data-testid attribute set to 'student-address-input'.
        WebElement addressInput = driver.findElement(
                By.cssSelector("[data-testid='student-address-input']")
        );

        WebElement submitButton = driver.findElement(
                By.cssSelector("[data-testid='submit-student-button']")
        );

        nameInput.sendKeys(studentName); // sendKeys() method is used to type the generated student name into the name input field.
        addressInput.sendKeys(studentAddress);
        submitButton.click(); // click() method is used to click on the submit button, which triggers the form submission to add the new student.

        wait.until(
                ExpectedConditions.textToBePresentInElementLocated( // wait until the new student name is visible on the page after submission, indicating that the student has been added successfully.
                        By.tagName("body"),
                        studentName
                )
        );

        String pageSource = driver.getPageSource(); // get the page source after adding the student to verify that the new student name and address are present in the page source, confirming that the student has been added successfully.

        assertTrue(pageSource.contains(studentName));
        assertTrue(pageSource.contains(studentAddress));

        System.out.println("Student added and verified successfully");
    }

    @Test
    void verifyStudentPageLoadsSuccessfully() { // verify that the student page loads successfully.
        driver.get("http://localhost:5173");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated( // wait until the element containing the text "Add Student" is visible on the page, which indicates that the student page has loaded successfully.
                        By.xpath("//*[contains(text(), 'Add Student')]")
                )
        );

        assertTrue(driver.getPageSource().contains("Add Student"));

        System.out.println("Student page loaded successfully");
    }

    @AfterEach // @AfterEach, which means it will be executed after each test method in the class. 
    // This method is responsible for closing the browser properly.
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
// mvn test

// @BeforeEach
//    ↓
// Open Chrome
//    ↓
// Open application
//    ↓
// Find input fields
//    ↓
// Enter student details
//    ↓
// Click submit
//    ↓
// Verify student added
//    ↓
// @AfterEach
// Close browser
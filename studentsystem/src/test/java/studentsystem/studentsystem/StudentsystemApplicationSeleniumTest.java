package studentsystem.studentsystem;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class StudentsystemApplicationSeleniumTest {

    @Test
    void openStudentFrontendPage() throws InterruptedException {

        // WebDriver is a Selenium interface for controlling web browsers. Here we use ChromeDriver to control the Chrome browser.
        WebDriver driver = new ChromeDriver(); // open Chrome browser

        try {
            // get() method is used to navigate to a specified URL.
            driver.get("http://localhost:5173"); // navigate to the frontend URL

            Thread.sleep(2000); // wait for 2 seconds to allow the page to load completely
            // getPageSource() method returns the source code of the current page as a string. We can use this to verify if the page has loaded correctly.
            String pageSource = driver.getPageSource();//  verify if the page is loaded

            // The assertTrue() method is used to assert that a condition is true. 
            // pageSource is a string containing the HTML and pageSource.length() gives the length of that string. If the length is greater than 0, it means the page source is not empty, indicating that the page has loaded successfully.
            assertTrue(pageSource.length() > 0); // check if the page source is not empty and see HTML content

            System.out.println("Frontend page opened successfully");// print success message

        } finally {
            driver.quit();// close the browser
        }
    }
}
// mvn test
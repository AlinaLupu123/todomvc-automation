//this is the code before refactoring and putting it into a Page Object Model//

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AutomationChallengeTest {

    private static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() { driver = new ChromeDriver(); }

    @Test
    void getPageTitle() throws Exception {

        // navigate to the React framework and asserted TodoMVC is the page title
        driver.get("https://todomvc.com/examples/react/dist/");
        String pageTitleText = driver.getTitle();
        assertEquals("TodoMVC: React", driver.getTitle());

        //click on the search box, type in items and enter, then assert the new to do items are there
        driver.findElement(By.id("todo-input")).sendKeys("Go shopping");
        driver.findElement(By.id("todo-input")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("todo-input")).sendKeys("Buy milk");
        driver.findElement(By.id("todo-input")).sendKeys(Keys.ENTER);
        List<String> items = List.of("Go shopping","Buy milk");
        assertTrue(items.contains("Go shopping"));
        assertTrue(items.contains("Buy milk"));

        //verify counter accuracy
        WebElement todoCount = driver.findElement(By.className("todo-count"));
        assertEquals("2 items left!", todoCount.getText());
        takeScreenshot(driver, "2-items-left.png");

        //mark individual items as complete
        driver.findElement(By.cssSelector("li:nth-child(1) .toggle")).click();
        takeScreenshot(driver, "complete-first-item.png");
        assertEquals("1 item left!", todoCount.getText());
        driver.findElement(By.cssSelector("li:nth-child(2) .toggle")).click();
        assertEquals("0 items left!", todoCount.getText());
        takeScreenshot(driver, "complete-both-items-individually.png");


        //mark individual items as incomplete
        driver.findElement(By.cssSelector("li:nth-child(1) .toggle")).click();
        takeScreenshot(driver, "uncompleted-first-item.png");
        assertEquals("1 item left!", todoCount.getText());
        driver.findElement(By.cssSelector("li:nth-child(2) .toggle")).click();
        assertEquals("2 items left!", todoCount.getText());
        takeScreenshot(driver, "uncompleted-second-item.png");
        //takeScreenshot(driver, "complete-second-item.png");

        //toggle all complete
        driver.findElement(By.id("toggle-all")).click();
        assertEquals("0 items left!", todoCount.getText());
        takeScreenshot(driver, "toggle-all-complete.png");

        //toggle all incomplete
        driver.findElement(By.id("toggle-all")).click();
        assertEquals("2 items left!", todoCount.getText());
        takeScreenshot(driver, "toggle-all-incomplete.png");

        //Delete an item
        // Locate the item box before hovering and clicking on the destroy button
        WebElement ItemBox = driver.findElement(By.cssSelector(".todo-list li"));
        System.out.println(ItemBox.isEnabled());
        // Hover over the item to make the delete button appear
        Actions actions = new Actions(driver);
        actions.moveToElement(ItemBox).perform();
        // Locate the delete button after the hover
        WebElement deleteButton = ItemBox.findElement(By.cssSelector(".todo-list li .destroy"));
        System.out.println(deleteButton.isEnabled());
        // Wait until the delete button is clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        // Click the delete button
        deleteButton.click();
        // Verify the remaining items
        assertEquals("1 item left!", todoCount.getText()); // Adjust text if needed
        takeScreenshot(driver, "delete-item.png");
    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }

    // Helper function for taking screenshots using WebDriver
    public static void takeScreenshot(WebDriver webdriver, String desiredPath) throws Exception{
        TakesScreenshot screenshot = ((TakesScreenshot)webdriver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(desiredPath);
        FileUtils.copyFile(screenshotFile, targetFile);
    }
}




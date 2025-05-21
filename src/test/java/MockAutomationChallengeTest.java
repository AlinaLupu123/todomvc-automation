import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ReactPage;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MockAutomationChallengeTest {

    private static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() {
        driver = new ChromeDriver();
    }

    @Test
    public void inputTodoItems_andcheckCounter() throws Exception {
        ReactPage reactPage = new ReactPage(driver);
        reactPage.navigate();
        reactPage.inputItem("Buy milk");
        reactPage.inputItem("Go shopping");
        List<WebElement> toDoList = reactPage.getTodoList();
        assertTrue(toDoList.get(1).getText().contains("Go shopping"));
        assertTrue(toDoList.get(0).getText().contains("Buy milk"));
        //String counterText = reactPage.getCounter();
        assertEquals("2 items left!", reactPage.getCounter());
        takeScreenshot(driver, "refactor-2-items-left.png");
        reactPage.clickToggleAll();
        //String updatedCounterText = reactPage.getCounter();
        takeScreenshot(driver, "refactor-toggle-all-incomplete.png");
        assertEquals("0 items left!", reactPage.getCounter());


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
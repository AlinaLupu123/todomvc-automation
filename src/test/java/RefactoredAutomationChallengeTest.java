//this is the refactored code//

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ReactPage;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RefactoredAutomationChallengeTest {

    private static ChromeDriver driver;
    private ReactPage reactPage;

    @BeforeAll
    static void launchBrowser() {
        driver = new ChromeDriver();
    }
//this code is executed before each of the tests below//
    @BeforeEach
    void navigateToPageAndAddTwoItems() {
        reactPage = new ReactPage(driver);
        reactPage.navigate();
        reactPage.inputItem("Buy milk");
        reactPage.inputItem("Go shopping");
    }
//checking that the items have been added to the to do list//
    @Test
    public void getTodoItems() {
        List<WebElement> toDoList = reactPage.getTodoList();
        assertTrue(toDoList.get(0).getText().contains("Buy milk"));
        assertTrue(toDoList.get(1).getText().contains("Go shopping"));
    }

    //checking the counter has updated accordingly//
    @Test
    public void getCounterText() throws Exception {
        assertEquals("2 items left!", reactPage.getCounter());
        //takeScreenshot(driver, "refactor-2-items-left.png");
    }

    //Checking that the First Item shows as complete after ticking the box next to it once, then shows as incomplete after ticking the same box again.
    //in a future refactor we could try to search by the "nth: child" instead of index
    // We have merged this into one test so we can keep the @/Before Each method//
    @Test
    public void markFirstItem_Complete_then_Incomplete() throws Exception {
        reactPage.clickFirstItem();
        assertEquals("1 item left!", reactPage.getCounter());
        //takeScreenshot(driver, "click_first_item.png");
        reactPage.clickFirstItem();
        assertEquals("2 items left!", reactPage.getCounter());
        //takeScreenshot(driver, "un_click_first_item.png");

    }


    //checking that all items show as complete after toggling once, then all items how as incomplete after toggling a second time.
    // We have merged this into one test so we can keep the @/Before Each method//
    @Test
    public void toggleAllItems_Complete_then_Incomplete() throws Exception {
        reactPage.clickToggleAll();
        assertEquals("0 items left!", reactPage.getCounter());
        //takeScreenshot(driver, "refactor-toggle-all-complete.png");
        reactPage.clickToggleAll();
        assertEquals("2 items left!", reactPage.getCounter());

    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }

    // Helper function for taking screenshots using WebDriver
    public static void takeScreenshot(WebDriver webdriver, String desiredPath) throws Exception {
        TakesScreenshot screenshot = ((TakesScreenshot) webdriver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(desiredPath);
        FileUtils.copyFile(screenshotFile, targetFile);
    }
}
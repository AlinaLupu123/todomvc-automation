import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.*;

public class AutomationChallengeTest {

    private static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() { driver = new ChromeDriver(); }

    @Test
    void printPageTitle() throws Exception {
        driver.get("https://makers.tech");
        String pageTitleText = driver.getTitle();
        assertTrue(pageTitleText.contains("Building the future"));

        WebElement codeOCLink = driver.findElement(By.linkText("Code of Conduct"));
        new Actions(driver).moveToElement(codeOCLink).perform();
        assertNotNull(codeOCLink);
        codeOCLink.click();

        String expectedUrl = "https://makers.tech/code-of-conduct";
        String actualUrl = driver.getCurrentUrl();
        assertEquals( expectedUrl, actualUrl);

        String codeOCText = driver.getTitle();
        assertTrue(codeOCText.contains("Code of conduct"));

        driver.navigate().back();
        assertEquals("https://makers.tech/", driver.getCurrentUrl());

        WebElement faqLink = driver.findElement(By.linkText("FAQ"));
        new Actions(driver).moveToElement(faqLink).perform();
        assertNotNull(faqLink);
        faqLink.click();

        Object[] windowHandles=driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);

        WebElement faqHeading = driver.findElement(By.cssSelector("h1.kb-search-section__title div.kb-search-section__title_inner"));
        String faqText = faqHeading.getText();
        assertTrue(faqText.contains("How can we help you?"));

        WebElement searchField = driver.findElement(By.cssSelector("input[class='kb-search__input']"));
        System.out.println("Displayed: " + searchField.isDisplayed());
        searchField.sendKeys("badger" + Keys.ENTER);


        WebElement searchResults = driver.findElement(By.cssSelector("h1"));
        Thread.sleep(1000);
        String resultText = searchResults.getText();
        assertTrue(resultText.contains ("No results for \"badger\""));

        HelloMakers.takeScreenshot(driver, "challenge.png");
    }

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }
}




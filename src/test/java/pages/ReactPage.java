package pages;

// in MakersSearchPage.java

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ReactPage {
    protected WebDriver driver;
    private By toDoInputBox = (By.id("todo-input"));
    private By toDoList = (By.cssSelector("ul.todo-list li"));
    private By counter = (By.className("todo-count"));
    private By toggleAll = (By.id("toggle-all"));
    private By firstItem = (By.cssSelector("li:nth-child(1) .toggle"));


    public ReactPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigate() {
        driver.get("https://todomvc.com/examples/react/dist/");
    }

    public void inputItem(String inputItem) {
        WebElement inputBox = driver.findElement(toDoInputBox);
        inputBox.sendKeys(inputItem);
        inputBox.sendKeys(Keys.ENTER);
    }

    public List <WebElement> getTodoList() {
    return driver.findElements(toDoList);
}

    public String getCounter() {
        return driver.findElement(counter).getText();
    }

    public void clickFirstItem() {
        WebElement tickBoxOne = driver.findElement(firstItem);
        tickBoxOne.click();
    }

    public void clickToggleAll() {
        WebElement toggleButton = driver.findElement(toggleAll);
        toggleButton.click();
    }

    //public void waitForResultsText(String resultsText) {
    // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    //wait.until(ExpectedConditions.textToBe(searchResultsHeadingBy,resultsText));
    // }
}



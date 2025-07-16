package com.practicetestautomation.tests.exceptions;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionsTests {
    private WebDriver driver;
    private Logger logger;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser){
        logger = Logger.getLogger(ExceptionsTests.class.getName());
        logger.setLevel(Level.INFO);
        logger.info("Running test in " + browser);
        switch (browser.toLowerCase()){
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }

        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
        logger.info("Browser is closed");
    }

    @Test
    public void noSuchElementExceptionTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        logger.info("Starting testLoginFunctionality");
        WebElement addButton = driver.findElement(By.xpath("//button[@id=\"add_btn\"]"));
        logger.info("Click Add button");
        addButton.click();

        WebElement row2InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));


        Assert.assertTrue(row2InputField.isDisplayed(), "Row 2 input field is not displayed");

        WebElement confirmationMessage = driver.findElement(By.id("confirmation"));
        confirmationMessage.isDisplayed();

    }

    @Test
    public void timeoutExceptionTest(){
        logger.info("Starting testLoginFunctionality");
        WebElement addButton = driver.findElement(By.xpath("//button[@id=\"add_btn\"]"));
        logger.info("Click Add button");
        addButton.click();

        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement row2InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
        } catch (TimeoutException e) {
            System.out.println("Element did not appear within expected time.");
        } finally {
            driver.quit();
        }
    }


    @Test
    public void elementNotInteractableExceptionTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        logger.info("Starting testLoginFunctionality");
        WebElement addButton = driver.findElement(By.xpath("//button[@id=\"add_btn\"]"));
        logger.info("Click Add button");
        addButton.click();

        WebElement inputField = driver.findElement(By.xpath("//div[@id='row2']/input"));
        inputField.sendKeys("Coffee");

        WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']/button[@name='Save']"));
        saveButton.click();

        WebElement messageConfirmation = driver.findElement(By.id("confirmation"));
        String actualMessage = messageConfirmation.getText();
        String expectedMessage = "Row 2 was saved";
        Assert.assertEquals(actualMessage, expectedMessage, "Message is not expected");

    }

    @Test
    public void invalidElementStateExceptionTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        logger.info("Starting testLoginFunctionality");

        WebElement editButton = driver.findElement(By.id("edit_btn"));
        editButton.click();

        WebElement inputField = driver.findElement(By.xpath("//div[@id='row1']/input"));
        inputField.clear();

        inputField.sendKeys("Hot-Dog");

        WebElement saveButton = driver.findElement(By.xpath("//div[@id='row1']/button[@name='Save']"));
        saveButton.click();

        Assert.assertEquals(inputField.getAttribute("value"), "Hot-Dog");

    }

}

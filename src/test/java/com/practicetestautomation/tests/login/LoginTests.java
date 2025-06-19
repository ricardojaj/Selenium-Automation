package com.practicetestautomation.tests.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTests {
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser){
       System.out.println("Running test in " + browser);
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

        driver.get("https://practicetestautomation.com/practice-test-login/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }

    @Test(groups = {"positive", "regression", "smoke"})
    public void testLoginFunctionality(){
        //Type username student into Username field
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("student");

        //Type password Password123 into Password field
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Password123");


        //Push Submit button
        WebElement submitBtn = driver.findElement(By.id("submit"));
        submitBtn.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Verify new page URL contains practicetestautomation.com/logged-in-successfully/
        String expectedUrl = "https://practicetestautomation.com/logged-in-successfully/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);


        //Verify new page contains expected text ('Congratulations' or 'successfully logged in')
        String expectedMessage = "Congratulations student. You successfully logged in!";
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains(expectedMessage));

        //Verify button Log out is displayed on the new page
        WebElement logOutButton = driver.findElement(By.linkText("Log out"));
        Assert.assertTrue(logOutButton.isDisplayed());

    }

    @Parameters({"username", "password", "expectedErrorMessage"})
    @Test(groups = {"negative", "regression"})
    public void negativeLoginTest(String username, String password, String expectedErrorMessage){
       /* Open page
        Type username incorrectUser into Username field
        Type password Password123 into Password field
        Push Submit button
        Verify error message is displayed
        Verify error message text is Your username is invalid!*/

        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys(username);


        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);


        WebElement submitBtn = driver.findElement(By.id("submit"));
        submitBtn.click();

        WebElement errorMessage = driver.findElement(By.id("error"));
        errorMessage.isDisplayed();

        String textError = errorMessage.getText();


        Assert.assertEquals(expectedErrorMessage, textError);

    }


}

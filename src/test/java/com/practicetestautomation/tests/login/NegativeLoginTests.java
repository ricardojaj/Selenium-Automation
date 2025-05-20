package com.practicetestautomation.tests.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeLoginTests {

    @Test
    public void incorrectUsernameTest(){
       /* Open page
        Type username incorrectUser into Username field
        Type password Password123 into Password field
        Push Submit button
        Verify error message is displayed
        Verify error message text is Your username is invalid!*/

        WebDriver driver = new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");


        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys("Username");


        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Password123");


        WebElement submitBtn = driver.findElement(By.id("submit"));
        submitBtn.click();

        WebElement errorMessage = driver.findElement(By.id("error"));
        errorMessage.isDisplayed();

        String textError = errorMessage.getText();
        String actualMessage = "Your username is invalid!";

        Assert.assertEquals(actualMessage, textError);
        driver.quit();

    }

    @Test
    public void incorrectPasswordTest(){

    }


}

package com.practicetestautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ExceptionsPage extends BasePage{

    private By addButtonLocator = By.id("adn_btn");
    private By row2InputField = By.xpath("//div[@id='row2']/input");


    public ExceptionsPage(WebDriver driver) {
        super(driver);
    }

    public void visit(){
        super.visit("https://practicetestautomation.com/practice-test-exceptions/");
    }

    public void pushAddButton(){
        driver.findElement(addButtonLocator).click();
    }

    public boolean isRowTwoDisplayed(){
        return waitForDisplayed(row2InputField);
    }


}

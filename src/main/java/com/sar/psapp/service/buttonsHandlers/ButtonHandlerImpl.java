package com.sar.psapp.service.buttonsHandlers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class ButtonHandlerImpl implements ButtonHandler {

    @Override
    public void handleAgeButton(WebDriver driver) {
        try {
            WebElement ageButton = driver.findElement(By.className("adult-disclaimer__btn"));
            ageButton.click();
        } catch (Exception e) {
            System.out.println("Кнопка возраста не найдена");
        }
    }

    @Override
    public void handleRegionButton(WebDriver driver) {
        try {
            WebElement submitButton = driver.findElement(By.className("header-region-selector-view__footer-ok"));
            submitButton.click();
        }
        catch (Exception e) {
            System.out.println("Кнопка региона не найдена");
        }
    }
}

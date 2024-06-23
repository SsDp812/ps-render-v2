package com.sar.psapp.service.buttonsHandlers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

@Component
public class ButtonHandlerImpl implements ButtonHandler {

    @Override
    public void handleAllButtons(WebDriver driver, int waitTime, int pageLimit) {
        this.handleAgeButton(driver, waitTime);
        this.handleRegionButton(driver, waitTime);
    }

    @Override
    public void handleAgeButton(WebDriver driver, int waitTime) {
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.className("adult-disclaimer__btn")));
            button.click();
        } catch (Exception e) {
            System.out.println("Кнопка возраста не найдена");
        }
    }

    @Override
    public void handleRegionButton(WebDriver driver, int waitTime) {
        try {
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.className("header-region-selector-view__footer-ok")));
            button.click();
        }
        catch (Exception e) {
            System.out.println("Кнопка региона не найдена");
        }
    }
}
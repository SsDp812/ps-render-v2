package com.sar.psapp.service.buttonsHandlers;

import org.openqa.selenium.WebDriver;

public interface ButtonHandler {
    public void handleAllButtons(WebDriver driver, int timeLimit, int pageLimit);
    public void handleAgeButton(WebDriver driver, int timeLimit);
    public void handleRegionButton(WebDriver driver, int timeLimit);
}

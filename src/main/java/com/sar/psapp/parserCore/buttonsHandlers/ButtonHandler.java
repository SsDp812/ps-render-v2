package com.sar.psapp.parserCore.buttonsHandlers;

import org.openqa.selenium.WebDriver;

public interface ButtonHandler {
    void handleAllButtons(WebDriver driver, int timeLimit, int pageLimit);
    void handleAgeButton(WebDriver driver, int timeLimit);
    void handleRegionButton(WebDriver driver, int timeLimit);
}

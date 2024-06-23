package com.sar.psapp.service;

import com.sar.psapp.dto.Card;
import com.sar.psapp.dto.StartProcess;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public interface ParserService {
    List<Card> parseBySettings(StartProcess settings);
    WebDriver initializeBaseDriver(List<Card> cardsList, StartProcess settings);
    void parsePages(List<Card> cardsList, int pageLimit, WebDriver baseDriver, StartProcess settings);
    WebDriver createNewWebDriver(WebDriver baseDriver, String taskUrl);
    List<Card> parsePage(WebDriver driver, StartProcess settings);
}

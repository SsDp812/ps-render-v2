package com.sar.psapp.service;

import com.sar.psapp.dto.Card;
import com.sar.psapp.dto.StartProcess;
import org.openqa.selenium.WebDriver;

import java.util.List;

public interface ParserService {
    List<Card> parseBySettings(StartProcess settings);
    List<Card> parsePage(WebDriver driver, StartProcess settings);
}

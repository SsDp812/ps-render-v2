package com.sar.psapp.parserCore;

import com.sar.psapp.dto.response.Card;
import com.sar.psapp.dto.generals.StartProcess;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ParserService {
    @Async
    CompletableFuture<List<Card>> parseBySettings(StartProcess settings,Long processId);
    List<Card> parsePage(WebDriver driver, StartProcess settings);
}

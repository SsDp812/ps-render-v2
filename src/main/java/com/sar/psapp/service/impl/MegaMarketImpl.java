package com.sar.psapp.service.impl;

import com.sar.psapp.dto.Card;
import com.sar.psapp.dto.StartProcess;
import com.sar.psapp.service.ParserService;
import com.sar.psapp.service.buttonsHandlers.ButtonHandler;
import com.sar.psapp.service.fileHandler.FileHandler;
import com.sar.psapp.service.validation.GoodsValidator;
import org.checkerframework.checker.units.qual.A;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class MegaMarketImpl implements ParserService {
    private final String MEGA_MARKET_BASE_URL = "https://megamarket.ru/";

    @Value("${app.conf.wait-time}")
    private int waitTime;

    @Autowired
    private GoodsValidator goodsValidator;

    @Autowired
    private ButtonHandler buttonHandler;

    @Autowired
    private FileHandler fileHandler;

    // parseBySettings инициализирует WebDriver и в цикле вызывает парсинг каждой страницы
    @Override
    public List<Card> parseBySettings(StartProcess settings) {
        int pageLimit = settings.getPageLimit();

        List<Card> cardsList = new ArrayList<>();

        WebDriver driver = new ChromeDriver();
        String url = settings.getCategory().getMegaMarketUrl();

        for (int pageNumber = 1; pageNumber <= pageLimit; pageNumber++) {
            if (pageNumber > 1) {
                url = settings.getCategory().getMegaMarketUrl() + "page-" + pageNumber + "/";
            }
            driver.get(url);
            System.out.println("URL текущей страницы: " + driver.getCurrentUrl());
            cardsList.addAll(this.parsePage(driver, settings));
        }

        driver.quit();

        fileHandler.csvFileHandler(cardsList);

        return cardsList;
    }

    // parsePage парсит страницу
    @Override
    public List<Card> parsePage(WebDriver driver, StartProcess settings) {
        int pageLimit = settings.getPageLimit();

        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.equals(settings.getCategory().getMegaMarketUrl())) {
            buttonHandler.handleAllButtons(driver, waitTime, pageLimit);
        }

        List<Card> cardsResponse = new ArrayList<>();
        Long start = System.currentTimeMillis();
        Document doc = Jsoup.parse(driver.getPageSource());
        Elements elements = doc.select(".catalog-item-mobile");
        for (Element card : elements) {
            Card cardResponse = new Card();
            try {
                Elements bonus = card.select(".item-bonus");
                cardResponse.setCardBonus(Long.parseLong(bonus.get(0).text()));
            } catch (Exception ex) {
                cardResponse.setCardBonus(0L);
            }

            Elements price = card.select(".item-price");
            String textPrice = price.get(0).text().substring(0, price.get(0).text().length() - 2);
            StringBuilder textPriceFormatted = new StringBuilder();
            for(char ch : textPrice.toCharArray()) {
                if(ch != ' '){
                    textPriceFormatted.append(ch);
                }
            }
            cardResponse.setCardPrice(Long.parseLong(textPriceFormatted.toString()));
            Elements name = card.select(".item-title");
            cardResponse.setCardName(name.get(0).text());

            Element link = name.get(0).selectFirst("a.ddl_product_link");
            String url = link.attr("href");
            cardResponse.setUrl(MEGA_MARKET_BASE_URL + url);

            if(goodsValidator.validateForBenefit(cardResponse,settings)){
                cardsResponse.add(cardResponse);
            }

        }
        Long stop = System.currentTimeMillis();
        System.out.println(stop - start);
        return cardsResponse;
    }
}

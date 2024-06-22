package com.sar.psapp.service.impl;

import com.sar.psapp.dto.Card;
import com.sar.psapp.dto.StartProcess;
import com.sar.psapp.service.ParserService;
import com.sar.psapp.service.buttonsHandlers.ButtonHandler;
import com.sar.psapp.service.validation.GoodsValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
public class MegaMarketImpl implements ParserService {
    private String MEGA_MARKET_BASE_URL = "https://megamarket.ru/";

    @Autowired
    private GoodsValidator goodsValidator;

    @Autowired
    private ButtonHandler buttonHandler;

    @Override
    public List<Card> parseBySettings(StartProcess settings) {
        WebDriver driver = new ChromeDriver();

        driver.get(settings.getCategory().getMegaMarketUrl());

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        buttonHandler.handleAgeButton(driver);
        buttonHandler.handleRegionButton(driver);

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
        driver.quit();
        Long stop = System.currentTimeMillis();
        System.out.println(stop - start);
        return cardsResponse;
    }
}

package com.sar.psapp.service.impl;

import com.sar.psapp.dto.Card;
import com.sar.psapp.dto.StartProcess;
import com.sar.psapp.service.ParserService;
import com.sar.psapp.service.buttonsHandlers.ButtonHandler;
import com.sar.psapp.service.fileHandler.FileHandler;
import com.sar.psapp.service.validation.GoodsValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

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

        Long start = System.currentTimeMillis();

        // Создание базового WebDriver и парсинг первой страницы для сохранения куков
        WebDriver baseDriver = initializeBaseDriver(cardsList, settings);
        // Многопоточный парсинг остальных страниц, используя куки базового WebDriver
        parsePages(cardsList, pageLimit, baseDriver, settings);
        baseDriver.quit();

        Long stop = System.currentTimeMillis();
        System.out.println(stop - start);

        fileHandler.csvFileHandler(cardsList);

        return cardsList;
    }

    // initializeBaseDriver создает базовый WebDriver и парсит первую страницу для сохранения куков
    @Override
    public WebDriver initializeBaseDriver(List<Card> cardsList, StartProcess settings) {
        WebDriver baseDriver = new ChromeDriver();

        String url = settings.getCategory().getMegaMarketUrl();
        baseDriver.get(url);
        System.out.println("URL текущей страницы: " + baseDriver.getCurrentUrl());

        cardsList.addAll(this.parsePage(baseDriver, settings));

        return baseDriver;
    }

    // parsePages запускает многопоточный парсинг страниц
    @Override
    public void parsePages(List<Card> cardsList, int pageLimit, WebDriver baseDriver, StartProcess settings) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<List<Card>>> futures = new ArrayList<>();

        for (int pageNumber = 2; pageNumber <= pageLimit; pageNumber++) {
            int taskPageNumber = pageNumber;

            Callable<List<Card>> task = () -> {
                String taskUrl = settings.getCategory().getMegaMarketUrl() + "page-" + taskPageNumber + "/";

                // Создание нового WebDriver с куками базового
                WebDriver taskDriver = createNewWebDriver(baseDriver, taskUrl);

                // Парсинг страницы с помощью созданного WebDriver
                List<Card> cards = parsePage(taskDriver, settings);
                taskDriver.quit();

                return cards;
            };

            futures.add(executorService.submit(task));
        }

        for (Future<List<Card>> future : futures) {
            try {
                cardsList.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
    }

    // createNewWebDriver возвращает новый WebDriver, копируя в него куки базового драйвера
    @Override
    public WebDriver createNewWebDriver(WebDriver baseDriver, String taskUrl) {
        WebDriver taskDriver = new ChromeDriver();

        taskDriver.get(taskUrl);
        System.out.println("URL текущей страницы: " + taskDriver.getCurrentUrl());

        for (org.openqa.selenium.Cookie cookie : baseDriver.manage().getCookies()) {
            taskDriver.manage().addCookie(cookie);
        }

        taskDriver.navigate().refresh();

        return taskDriver;
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
            for (char ch : textPrice.toCharArray()) {
                if (ch != ' ') {
                    textPriceFormatted.append(ch);
                }
            }
            cardResponse.setCardPrice(Long.parseLong(textPriceFormatted.toString()));
            Elements name = card.select(".item-title");
            cardResponse.setCardName(name.get(0).text());

            Element link = name.get(0).selectFirst("a.ddl_product_link");
            String url = link.attr("href");
            cardResponse.setUrl(MEGA_MARKET_BASE_URL + url);

            if (goodsValidator.validateForBenefit(cardResponse, settings)) {
                cardsResponse.add(cardResponse);
            }

        }
        return cardsResponse;
    }
}

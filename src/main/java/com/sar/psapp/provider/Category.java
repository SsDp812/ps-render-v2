package com.sar.psapp.provider;

public enum Category {

    TECH("Техника", "https://megamarket.ru/catalog/elektronika/", null),
    SEX("Секс игрушки", "https://megamarket.ru/catalog/tovary-dlya-vzroslyh/", null),
    PRODUCTS("Продукты", "https://megamarket.ru/catalog/produkty-pitaniya/", null),
    ;
    private String name, megaMarketUrl, yandexMarketUrl;

    Category(String name, String megaMarketUrl, String yandexMarketUrl) {
        this.name = name;
        this.megaMarketUrl = megaMarketUrl;
        this.yandexMarketUrl = yandexMarketUrl;
    }

    public String getName() {
        return name;
    }

    public String getMegaMarketUrl() {
        return megaMarketUrl;
    }

    public String getYandexMarketUrl() {
        return yandexMarketUrl;
    }
}

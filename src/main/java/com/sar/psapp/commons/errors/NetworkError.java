package com.sar.psapp.commons.errors;

public enum NetworkError {
    ERROR_WHILE_CONNECTION_BINANCE("Ошибка при подключении к binance"),
    UNEXPECTED_DATE_FROM_EXCHANGE_API("Непредвиденный формат данных от binance");

    public String message;
    NetworkError(String s) {
        this.message = s;
    }
}

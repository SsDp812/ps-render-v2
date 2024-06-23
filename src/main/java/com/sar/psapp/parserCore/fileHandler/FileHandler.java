package com.sar.psapp.parserCore.fileHandler;

import com.sar.psapp.dto.response.Card;

import java.util.List;

public interface FileHandler {
    String csvFileHandler(List<Card> cards);
}

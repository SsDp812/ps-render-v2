package com.sar.psapp.service.fileHandler;

import com.sar.psapp.dto.Card;

import java.util.List;

public interface FileHandler {
    void csvFileHandler(List<Card> cards);
}

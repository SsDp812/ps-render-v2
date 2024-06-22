package com.sar.psapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Card {
    private String cardName;
    private Long cardPrice;
    private Long cardBonus;
    private String url;
}

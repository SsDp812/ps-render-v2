package com.sar.psapp.service.validation;

import com.sar.psapp.dto.Card;
import com.sar.psapp.dto.StartProcess;
import org.springframework.stereotype.Component;

@Component
public class GoodsValidator {

    public boolean validateForBenefit(Card card, StartProcess settings){
        if(settings.getBonusPerc() != null){
            if(((float)card.getCardBonus() / ((float)(card.getCardPrice() / 100))) < settings.getBonusPerc()){
                return false;
            }
        }
        if(settings.getMaxProductPrice() != null){
            if(card.getCardPrice() > settings.getMaxProductPrice()){
                return false;
            }
        }
        if(settings.getMinProductPrice() != null){
            return card.getCardPrice() >= settings.getMinProductPrice();
        }
        return true;
    }
}

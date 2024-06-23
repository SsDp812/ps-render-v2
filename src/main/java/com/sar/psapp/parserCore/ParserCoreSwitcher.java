package com.sar.psapp.parserCore;

import com.sar.psapp.dto.generals.StartProcess;
import com.sar.psapp.parserCore.impl.MegaMarketImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParserCoreSwitcher {
    @Autowired
    private ParserService megaMarketService;

    public ParserService getService(StartProcess settings) {
        switch (settings.getProvider()) {
            case MEGA_MARKET:
                return megaMarketService;
            case YANDEX_MARKET:
                System.out.println("Yandex market not supported now");
                return null;
            default:
                return null;
        }
    }
}

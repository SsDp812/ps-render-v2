package com.sar.psapp.service;

import com.sar.psapp.dto.StartProcess;
import com.sar.psapp.service.impl.MegaMarketImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceSwitcher {
    @Autowired
    private MegaMarketImpl megaMarketService;

    public ParserService getService(StartProcess settings){
        switch (settings.getProvider()){
            case MEGA_MARKET -> {
                return megaMarketService;
            }
            case YANDEX_MARKET -> {
                System.out.println("Yandex market not supported now");
                return null;
            }
            default -> {
                return null;
            }
        }
    }
}

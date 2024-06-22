package com.sar.psapp.rest;

import com.sar.psapp.dto.Card;
import com.sar.psapp.dto.StartProcess;
import com.sar.psapp.service.ParserService;
import com.sar.psapp.service.ServiceSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/process")
public class StartProcessController {
    @Autowired
    private ServiceSwitcher switcher;

    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Card> start(@RequestBody StartProcess settings) {
        ParserService service = switcher.getService(settings);
        return service.parseBySettings(settings);
    }
}

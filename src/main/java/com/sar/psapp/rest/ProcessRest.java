package com.sar.psapp.rest;

import com.sar.psapp.commons.BaseDataResponse;
import com.sar.psapp.dto.response.Card;
import com.sar.psapp.dto.generals.StartProcess;
import com.sar.psapp.dto.response.ProcessView;
import com.sar.psapp.parserCore.ParserService;
import com.sar.psapp.parserCore.ParserCoreSwitcher;
import com.sar.psapp.service.ProcessInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process")
public class ProcessRest {
    @Autowired
    private ProcessInfoService service;

    @PostMapping(value = "/start", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<ProcessView> start(@RequestBody StartProcess settings) {
       return service.activateNewProcess(settings);
    }

    @GetMapping(value = "/getMyProcesses")
    public BaseDataResponse<ProcessView> getMyProcess() {
        return service.getAllUserProcesses();
    }
}

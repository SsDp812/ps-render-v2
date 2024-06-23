package com.sar.psapp.service.impl;

import com.sar.psapp.auth.AuthService;
import com.sar.psapp.commons.BaseDataResponse;
import com.sar.psapp.commons.states.ProcessState;
import com.sar.psapp.dto.generals.StartProcess;
import com.sar.psapp.dto.response.ProcessView;
import com.sar.psapp.mapper.ProcessMapper;
import com.sar.psapp.model.AppUser;
import com.sar.psapp.model.ProcessInfo;
import com.sar.psapp.parserCore.ParserCoreSwitcher;
import com.sar.psapp.parserCore.ParserService;
import com.sar.psapp.repositories.ProcessInfoRepository;
import com.sar.psapp.service.ProcessInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProcessInfoImpl implements ProcessInfoService {
    @Autowired
    private AuthService authService;
    @Autowired
    private ProcessInfoRepository processInfoRepository;
    @Autowired
    private ProcessMapper processMapper;
    @Autowired
    private ParserCoreSwitcher switcher;

    @Override
    public BaseDataResponse<ProcessView> getAllUserProcesses() {
        BaseDataResponse<List<ProcessView>> response = new BaseDataResponse<>();
        BaseDataResponse<AppUser> user = authService.getCurrentAccount();
        if (!user.getSuccess()) {
            response.addErrors(response.getErrors());
            return response.build();
        }
        List<ProcessInfo> processes = processInfoRepository.findByUser(user.getData());
        List<ProcessView> views = new ArrayList<>();
        processes.forEach(p -> {
            views.add(processMapper.mapToView(p));
        });
        response.setData(views);
        return response.build();
    }

    @Override
    public BaseDataResponse<ProcessView> activateNewProcess(StartProcess settings) {
        //TODO валидация процесса нужна перед запуском
        BaseDataResponse<ProcessView> response = new BaseDataResponse<>();
        BaseDataResponse<AppUser> user = authService.getCurrentAccount();
        if (!user.getSuccess()) {
            response.addErrors(response.getErrors());
            return response.build();
        }
        ProcessInfo processInfo = new ProcessInfo();
        processInfo.setUser(user.getData());
        //TODO после реализации очередей процессов сначала надо будет их сохранять как черновик
        processInfo.setState(ProcessState.ACTIVE);
        processInfo.setGuid(UUID.randomUUID().toString());
        processInfo.setFixedSettings(settings);
        processInfo = processInfoRepository.save(processInfo);
        log.debug("process started!");
        ParserService service = switcher.getService(settings);
        service.parseBySettings(settings, processInfo.getId());
        response.setData(processMapper.mapToView(processInfo));
        return response.build();
    }
}

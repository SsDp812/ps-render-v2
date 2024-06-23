package com.sar.psapp.service;

import com.sar.psapp.commons.BaseDataResponse;
import com.sar.psapp.dto.generals.StartProcess;
import com.sar.psapp.dto.response.ProcessView;

public interface ProcessInfoService {
    public BaseDataResponse<ProcessView> getAllUserProcesses();
    public BaseDataResponse<ProcessView> activateNewProcess(StartProcess settings);
}

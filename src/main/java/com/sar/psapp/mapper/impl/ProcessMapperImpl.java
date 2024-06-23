package com.sar.psapp.mapper.impl;

import com.sar.psapp.commons.states.ProcessState;
import com.sar.psapp.dto.response.ProcessView;
import com.sar.psapp.mapper.ProcessMapper;
import com.sar.psapp.model.ProcessInfo;
import org.springframework.stereotype.Component;

@Component
public class ProcessMapperImpl implements ProcessMapper {
    @Override
    public ProcessView mapToView(ProcessInfo process) {
        ProcessView view = new ProcessView();
        view.setId(process.getId());
        view.setSettings(process.getFixedSettings());
        view.setState(process.getState());
        if(process.getState().equals(ProcessState.COMPLETED)){
            //TODO сделать маппинг ссылки на файл
            view.setFileUrl(process.getFileGuid());
        }
        return view;
    }
}
